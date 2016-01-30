package de.randombyte.nightmare_ai.config

object DefaultMap {
  def create[CatalogType, AdditionalType](defaultKeys: List[CatalogType],
                                          defaultValue: AdditionalType): Map[CatalogType, AdditionalType] =
    defaultKeys.map { key => key -> defaultValue }.toMap[CatalogType, AdditionalType]
}
