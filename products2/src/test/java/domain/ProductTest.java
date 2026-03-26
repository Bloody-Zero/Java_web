package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    
    private Product product;
    private Manufacturer manufacturer;
    
    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer(1L, "ТестПроизводитель", "Россия", "Иванов", "123-45-67");
        product = new Product();
    }
    
    @Nested
    @DisplayName("Тесты конструкторов")
    class ConstructorTests {
        
        @Test
        @DisplayName("Пустой конструктор должен создавать объект")
        void testEmptyConstructor() {
            Product p = new Product();
            assertNotNull(p);
        }
        
        @Test
        @DisplayName("Конструктор с параметрами (без id) должен правильно устанавливать поля")
        void testConstructorWithParams() {
            Product p = new Product("Ноутбук", "15.6 дюймов", 2.5, manufacturer);
            
            assertEquals("Ноутбук", p.getName());
            assertEquals("15.6 дюймов", p.getSize());
            assertEquals(2.5, p.getWeight());
            assertEquals(manufacturer, p.getManufacturer());
            assertEquals(manufacturer.getId(), p.getManufacturerId());
            assertNull(p.getId());
        }
        
        @Test
        @DisplayName("Полный конструктор должен правильно устанавливать все поля")
        void testFullConstructor() {
            Product p = new Product(1L, "Ноутбук", "15.6 дюймов", 2.5, 1L, manufacturer);
            
            assertEquals(1L, p.getId());
            assertEquals("Ноутбук", p.getName());
            assertEquals("15.6 дюймов", p.getSize());
            assertEquals(2.5, p.getWeight());
            assertEquals(1L, p.getManufacturerId());
            assertEquals(manufacturer, p.getManufacturer());
        }
    }
    
    @Nested
    @DisplayName("Тесты геттеров и сеттеров")
    class GetterSetterTests {
        
        @Test
        @DisplayName("Сеттер и геттер для id должны работать корректно")
        void testIdGetterSetter() {
            product.setId(5L);
            assertEquals(5L, product.getId());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для name должны работать корректно")
        void testNameGetterSetter() {
            product.setName("iPhone 15");
            assertEquals("iPhone 15", product.getName());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для size должны работать корректно")
        void testSizeGetterSetter() {
            product.setSize("6.1 дюймов");
            assertEquals("6.1 дюймов", product.getSize());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для weight должны работать корректно")
        void testWeightGetterSetter() {
            product.setWeight(0.18);
            assertEquals(0.18, product.getWeight());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для manufacturerId должны работать корректно")
        void testManufacturerIdGetterSetter() {
            product.setManufacturerId(3L);
            assertEquals(3L, product.getManufacturerId());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для manufacturer должны работать корректно")
        void testManufacturerGetterSetter() {
            product.setManufacturer(manufacturer);
            assertEquals(manufacturer, product.getManufacturer());
            assertEquals(manufacturer.getId(), product.getManufacturerId());
        }
        
        @Test
        @DisplayName("getManufacturerName должен возвращать название производителя")
        void testGetManufacturerName() {
            product.setManufacturer(manufacturer);
            assertEquals("ТестПроизводитель", product.getManufacturerName());
        }
        
        @Test
        @DisplayName("getManufacturerName должен возвращать 'Не указан' если производитель null")
        void testGetManufacturerNameNull() {
            product.setManufacturer(null);
            assertEquals("Не указан", product.getManufacturerName());
        }
    }
    
    @Nested
    @DisplayName("Тесты метода toString")
    class ToStringTests {
        
        @Test
        @DisplayName("toString должен возвращать корректное строковое представление")
        void testToString() {
            Product p = new Product(1L, "Ноутбук", "15.6 дюймов", 2.5, 1L, manufacturer);
            
            String expected = "Product {id = 1, name = 'Ноутбук', size = '15.6 дюймов', weight = 2.5, manufacturer = ТестПроизводитель}";
            assertEquals(expected, p.toString());
        }
    }
}