
class BuildingCategory:
    def __init__(self, id=None, name=None):
        self.id: int = id
        self.name: str = name

    def __eq__(self, other):
        return self.id == other.id

    def __str__(self):
        return f"{self.name} (id={self.id})"

    def __repr__(self):
        return self.__str__()


class BuildingEnum:
    def __init__(self, id=None, name=None, category=None):
        self.id: int = id
        self.name: str = name
        self.category: BuildingCategory = category

    def __eq__(self, other):
        return self.id == other.id

    def __str__(self):
        return f"{self.name} (id={self.id})"

    def __repr__(self):
        return self.__str__()


class DistrictEnum:
    def __init__(self, id=None, name=None):
        self.id: int = id
        self.name: str = name

    def __eq__(self, other):
        return self.id == other.id

    def __str__(self):
        return f"{self.name} (id={self.id})"


    def __repr__(self):
        return self.__str__()

