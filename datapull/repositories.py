from entities import *


class BuildingCategoryRepository:
    def __init__(self, db):
        cursor = db.cursor()
        cursor.execute("SELECT id, name FROM building_category")
        self._categories = [BuildingCategory(row_id, row_name) for row_id, row_name in cursor]
        cursor.close()

    def find_by_id(self, category_id: int) -> BuildingCategory:
        for category in self._categories:
            if category.id == category_id:
                return category
        return None

    def find_by_name(self, category_name: str) -> BuildingCategory:
        """Case Insensitive Search"""
        for category in self._categories:
            if category.name.lower() == category_name.lower():
                return category
        return None

    def find_by_names(self, category_names: list[str]) -> BuildingEnum:
        """Case Insensitive Search"""
        lower_names = [n.lower() for n in category_names]
        return [c for c in self._categories if c.name.lower() in lower_names]

    def find_all(self) -> list[BuildingCategory]:
        return self._categories


class BuildingEnumRepository:

    def __init__(self, db):
        cursor = db.cursor()
        self._category_repo = BuildingCategoryRepository(db)
        cursor.execute("SELECT id, name, category_id FROM building_enum")
        self._buildings = [BuildingEnum(i, n, self._category_repo.find_by_id(c)) for i, n, c in cursor]
        cursor.close()

    def find_by_id(self, building_id: int) -> BuildingEnum:
        for building in self._buildings:
            if building.id == building_id:
                return building
        return None

    def find_by_name(self, building_name: str) -> BuildingEnum:
        """Case Insensitive Search"""
        for building in self._buildings:
            if building.name.lower() == building_name.lower():
                return building
        return None


    def find_by_category(self, category: BuildingCategory) -> list[BuildingEnum]:
        return [building for building in self._buildings if building.category == category]

    def find_all(self) -> list[BuildingEnum]:
        return self._buildings


class DistrictEnumRepository:
    def __init__(self, db):
        cursor = db.cursor()
        cursor.execute("SELECT id, name FROM district_enum")
        self._districts = [DistrictEnum(row_id, row_name) for row_id, row_name in cursor]
        cursor.close()

    def find_by_id(self, district_id: int) -> DistrictEnum:
        for district in self._districts:
            if district.id == district_id:
                return district
        return None

    def find_by_name(self, district_name: str) -> DistrictEnum:
        """Case Insensitive Search"""
        for district in self._districts:
            if district.name.lower() == district_name.lower():
                return district
        return None


    def find_all(self) -> list[DistrictEnum]:
        return self._districts
