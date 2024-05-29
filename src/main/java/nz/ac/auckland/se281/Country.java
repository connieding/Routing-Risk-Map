package nz.ac.auckland.se281;

/** Represents a country. */
public class Country {
  private String countryName;
  private String continent;
  private int taxFees;

  /**
   * Constructor to create a country object.
   *
   * @param countryName the name of the country
   * @param continent the continent of the country
   * @param taxFees the tax fees of the country
   */
  public Country(String countryName, String continent, int taxFees) {
    this.countryName = countryName;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  /**
   * Get the string representation of the country.
   *
   * @return the string representation of the country
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
    return result;
  }

  /**
   * Check if the country is equal to another object.
   *
   * @param obj the object to compare
   * @return true if the country is equal to the object, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Country other = (Country) obj;
    if (countryName == null) {
      if (other.countryName != null) {
        return false;
      }
    } else if (!countryName.equals(other.countryName)) {
      return false;
    }
    return true;
  }

  /**
   * Get the name of the country.
   *
   * @return the name of the country
   */
  public String getCountryName() {
    return countryName;
  }

  /**
   * Get the continent of the country.
   *
   * @return the continent of the country
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Get the tax fees of the country.
   *
   * @return the tax fees of the country
   */
  public int getTaxFees() {
    return taxFees;
  }
}
