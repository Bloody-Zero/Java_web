package domain;

/**
 * Класс данных о производителях товаров
 */
public class Manufacturer {
    
    // Идентификатор производителя (первичный ключ)
    private Long id;
    
    // Название компании-производителя
    private String name;
    
    // Страна производителя
    private String country;
    
    // Контактное лицо
    private String contactPerson;
    
    // Телефон контактного лица
    private String phone;
    
    /**
     * Конструктор по умолчанию (без параметров)
     */
    public Manufacturer() {
    }
    
    /**
     * Конструктор с параметрами (без id)
     */
    public Manufacturer(String name, String country, String contactPerson, String phone) {
        this.name = name;
        this.country = country;
        this.contactPerson = contactPerson;
        this.phone = phone;
    }
    
    /**
     * Конструктор со всеми параметрами (включая id)
     */
    public Manufacturer(Long id, String name, String country, String contactPerson, String phone) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.contactPerson = contactPerson;
        this.phone = phone;
    }
    
    // Геттеры и сеттеры для всех полей
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * Переопределение метода toString() для удобного вывода информации
     */
    @Override
    public String toString() {
        return "Manufacturer {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", country = '" + country + '\'' +
                ", contactPerson = '" + contactPerson + '\'' +
                ", phone = '" + phone + '\'' +
                '}';
    }
}