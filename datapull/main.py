import requests
from bs4 import BeautifulSoup
import mysql.connector
import os
from structs import *
from repositories import *

db = mysql.connector.connect(
    host="localhost",
    user=os.getenv("DB_USER_3306"),
    password=os.getenv("DB_PASS_3306"),
    database="clan_capital",
    port=3306
)

district_repo = DistrictEnumRepository(db)
building_repo = BuildingEnumRepository(db)
category_repo = BuildingCategoryRepository(db)


barracks_category = category_repo.find_by_name("Barracks")
spell_factory_category = category_repo.find_by_name("Spell Factory")
capital_hall = district_repo.find_by_id(1)



    
    
        


def process_offense(building: BuildingEnum) -> BuildingData:
    building_data = BuildingData(building)
    if building.category.name == "Barracks":
        url = "https://clashofclans.fandom.com/wiki/Clan_Capital_Barracks"
    else:
        url = "https://clashofclans.fandom.com/wiki/Clan_Capital_Spell_Factories"
    response = requests.get(url)
    if response.status_code != 200:
        print("Failed to find")

    soup = BeautifulSoup(response.text, 'html.parser')

    span_id = building.name.replace(" ", "_")

    # Extract the data from the first #number-available-table
    stats = soup.find(attrs={"id": span_id}).parent.find_next_sibling("center").find_next_sibling("center")
    district_data = stats.select_one("#number-available-table")
    level_data = stats.select("table.wikitable")[-1]


    district_name = stats\
        .select_one("#number-available-table")\
        .select('#number-available-data-row')[-1]\
        .find('th').text.strip()

    building_data.add_district(district_repo.find_by_name(district_name), 1)
    
    upgrade_rows = level_data.select('tr')
    for row in upgrade_rows:
        row_data = row.select('td')
        if len(row_data):  # Check it isn't a header <th>
            level_number = row_data[0].get_text().strip()
            level_cost = row_data[2].get_text().strip()
            building_data.add_upgrade(level_number, level_cost)

    return building_data


def process_building(building: BuildingEnum) -> BuildingData:
    building_data = BuildingData(building)

    # Column on the wikitable to use for prices
    if building.category in category_repo.find_by_names(["Other", "Trap"]):
        col_index = 2
    elif building.category == category_repo.find_by_name("Army"):
        col_index = 3
    else:
        col_index = 4

    url_suffix = "/Clan_Capital"
    url = "https://clashofclans.fandom.com/wiki/" + building.name.replace(" ", "_") + url_suffix

    response = requests.get(url)

    # Check if the page exists, if not, remove "/Clan_Capital" and try again
    if response.status_code == 404:
        url = url.replace(url_suffix, "")
        response = requests.get(url)

    if response.status_code != 200:
        print("Failed to find")


    soup = BeautifulSoup(response.text, 'html.parser')

    # Extract the data from the first #number-available-table
    first_number_available_table = soup.select_one('#number-available-table')

    if first_number_available_table:
        last_td = first_number_available_table.find_all('td')[-1]
        building_data.add_district(capital_hall, int(last_td.get_text().strip()))

    # Extract the data from the second #number-available-table
    if building != capital_hall:  # skip clan capital
        second_number_available_table = soup.select('#number-available-table')[-1]
        data_rows = second_number_available_table.select('#number-available-data-row')

        for row in data_rows:
            district_name = row.find('th').get_text().strip()
            district_instance = district_repo.find_by_name(district_name)
            number = int(row.find_all('td')[-1].get_text().strip())
            building_data.add_district(district_instance, number)

    # Extract data from the div.stats-background > div.wikitable
    wikitable = soup.select_one('div.stats-background > table.wikitable')
    upgrade_rows = wikitable.select('tr')
    for row in upgrade_rows:
        row_data = row.select('td')
        if len(row_data):  # Check it isn't a header <th>
            level_number = row_data[0].get_text().strip()
            level_cost = row_data[col_index].get_text().strip()
            building_data.add_upgrade(level_number, level_cost)

    return building_data



def process_building_abstract(building: BuildingEnum) -> BuildingData:
    print(f"Processing {building.name}")
    if building.category in [barracks_category, spell_factory_category]:
        return process_offense(building)
    else:
        return process_building(building)



data: list[BuildingData] = []
for building in building_repo.find_all():
    data.append(process_building_abstract(building))


building_upgrade_inserts = []
district_building_limit_inserts = []

for building_data in data:
    building = building_data.get_building()

    for upgrade in building_data.get_upgrade_data().get():
        sql_val = f"({building.id}, {upgrade.level}, {upgrade.cost})"
        building_upgrade_inserts.append(sql_val)

    for district_datum in building_data.get_district_data().get():
        sql_val = f"({district_datum.district.id}, {building.id}, {district_datum.limit})"
        district_building_limit_inserts.append(sql_val)

values = ",\n".join(building_upgrade_inserts)
building_upgrade_insert = f"\
    INSERT INTO building_upgrade\
    (`building_enum_id`, `level`, `cost`)\
    VALUES \n{values} AS vals\
    ON DUPLICATE KEY UPDATE `cost` = vals.`cost`;"


values = ",\n".join(district_building_limit_inserts)
district_limit_insert = f"\
    INSERT INTO district_building_limit\
    (`district_enum_id`, `building_enum_id`, `maximum_amount`)\
    VALUES \n{values} AS vals\
    ON DUPLICATE KEY UPDATE `maximum_amount` = vals.`maximum_amount`;"


with open("building_upgrade_insert.txt", "w") as file:
    file.write(building_upgrade_insert)

with open("district_limit_insert.txt", "w") as file:
    file.write(district_limit_insert)

cursor = db.cursor()
cursor.execute(building_upgrade_insert)
cursor.close()

cursor = db.cursor()
cursor.execute(district_limit_insert)
cursor.close()

db.commit()
db.close()

#
# for k, v in building_enum_data.items():
#     if k < 27:
#         continue
#     data, levels = process_building(k, v)
#     print(v)
#     for x in data:
#         print(x)
#     for x in levels:
#         print(x)
#     print("\n\n")


