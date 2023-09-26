from entities import *


class DistrictDatum:
    def __init__(self, district: DistrictEnum, limit: int):
        self.district: DistrictEnum = district
        self.limit: int = limit


class UpgradeDatum:
    def __init__(self, upgrade_level: int, upgrade_cost: int):
        self.level: int = upgrade_level
        self.cost: int = upgrade_cost




class DistrictData:
    def __init__(self):
        self._data: list[DistrictDatum] = []

    def add_district(self, district: DistrictEnum, limit: int) -> None:
        self._data.append(DistrictDatum(district, limit))

    def get(self) -> list[DistrictDatum]:
        return self._data


class UpgradeData:
    def __init__(self):
        self._data: list[UpgradeDatum] = []

    def add_upgrade(self, level_number: str, cost: str) -> None:
        processed_cost = cost.replace(",", "")
        if processed_cost == "N/A":
            processed_cost = 0
        self._data.append(UpgradeDatum(int(level_number), processed_cost))

    def get(self) -> list[UpgradeDatum]:
        return self._data




class BuildingData:
    def __init__(self, building: BuildingEnum):
        self._building: BuildingEnum = building
        self._district_data: DistrictData = DistrictData()
        self._upgrade_data: UpgradeData = UpgradeData()

    def add_district(self, district: DistrictEnum, limit: int) -> None:
        self._district_data.add_district(district, limit)

    def add_upgrade(self, level_number: str, cost: str) -> None:
        self._upgrade_data.add_upgrade(level_number, cost)

    def get_district_data(self) -> DistrictData:
        return self._district_data

    def get_upgrade_data(self) -> UpgradeData:
        return self._upgrade_data

    def get_building(self) -> BuildingEnum:
        return self._building

