package domain;

/**
 * Класс данных о товарах
 */
public class Product {
    
    // Идентификатор товара (первичный ключ)
    private Long id;
    
    // Наименование товара
    private String name;
    
    // Размер товара
    private String size;
    
    // Вес товара
    private Double weight;
    
    // Внешний ключ - ссылка на сущность Manufacturer
    private Long manufacturerId;
    
    // Навигационное свойство - ссылка на производителя
    private Manufacturer manufacturer;
    
    /**
     * Конструктор по умолчанию (без параметров)
     */
    public Product() {
    }
    
    /**
     * Конструктор с параметрами (без id)
     */
    public Product(String name, String size, Double weight, Manufacturer manufacturer) {
        this.name = name;
        this.size = size;
        this.weight = weight;
        this.manufacturer = manufacturer;
        if (manufacturer != null) {
            this.manufacturerId = manufacturer.getId();
        }
    }
    
    /**
     * Конструктор с параметрами (без id, с id производителя)
     */
    public Product(String name, String size, Double weight, Long manufacturerId, Manufacturer manufacturer) {
        this.name = name;
        this.size = size;
        this.weight = weight;
        this.manufacturerId = manufacturerId;
        this.manufacturer = manufacturer;
    }
    
    /**
     * Конструктор со всеми параметрами (включая id)
     */
    public Product(Long id, String name, String size, Double weight, Long manufacturerId, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.weight = weight;
        this.manufacturerId = manufacturerId;
        this.manufacturer = manufacturer;
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
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Long getManufacturerId() {
        return manufacturerId;
    }
    
    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        if (manufacturer != null) {
            this.manufacturerId = manufacturer.getId();
        }
    }
    
    /**
     * Получение названия производителя через навигационное свойство
     */
    public String getManufacturerName() {
        return manufacturer != null ? manufacturer.getName() : "Не указан";
    }
    
    /**
     * Переопределение метода toString() для удобного вывода информации
     */
    @Override
    public String toString() {
        return "Product {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", size = '" + size + '\'' +
                ", weight = " + weight +
                ", manufacturer = " + getManufacturerName() +
                '}';
    }
}