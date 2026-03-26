package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {
    
    private Manufacturer manufacturer;
    
    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer();
    }
    
    @Nested
    @DisplayName("Тесты конструкторов")
    class ConstructorTests {
        
        @Test
        @DisplayName("Пустой конструктор должен создавать объект")
        void testEmptyConstructor() {
            Manufacturer m = new Manufacturer();
            assertNotNull(m);
        }
        
        @Test
        @DisplayName("Конструктор с параметрами (без id) должен правильно устанавливать поля")
        void testConstructorWithParams() {
            Manufacturer m = new Manufacturer("ООО Тест", "Россия", "Иванов И.И.", "+7 (495) 123-45-67");
            
            assertEquals("ООО Тест", m.getName());
            assertEquals("Россия", m.getCountry());
            assertEquals("Иванов И.И.", m.getContactPerson());
            assertEquals("+7 (495) 123-45-67", m.getPhone());
            assertNull(m.getId());
        }
        
        @Test
        @DisplayName("Полный конструктор должен правильно устанавливать все поля")
        void testFullConstructor() {
            Manufacturer m = new Manufacturer(1L, "ООО Тест", "Россия", "Иванов И.И.", "+7 (495) 123-45-67");
            
            assertEquals(1L, m.getId());
            assertEquals("ООО Тест", m.getName());
            assertEquals("Россия", m.getCountry());
            assertEquals("Иванов И.И.", m.getContactPerson());
            assertEquals("+7 (495) 123-45-67", m.getPhone());
        }
    }
    
    @Nested
    @DisplayName("Тесты геттеров и сеттеров")
    class GetterSetterTests {
        
        @Test
        @DisplayName("Сеттер и геттер для id должны работать корректно")
        void testIdGetterSetter() {
            manufacturer.setId(5L);
            assertEquals(5L, manufacturer.getId());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для name должны работать корректно")
        void testNameGetterSetter() {
            manufacturer.setName("Samsung Electronics");
            assertEquals("Samsung Electronics", manufacturer.getName());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для country должны работать корректно")
        void testCountryGetterSetter() {
            manufacturer.setCountry("Южная Корея");
            assertEquals("Южная Корея", manufacturer.getCountry());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для contactPerson должны работать корректно")
        void testContactPersonGetterSetter() {
            manufacturer.setContactPerson("Kim D.S.");
            assertEquals("Kim D.S.", manufacturer.getContactPerson());
        }
        
        @Test
        @DisplayName("Сеттер и геттер для phone должны работать корректно")
        void testPhoneGetterSetter() {
            manufacturer.setPhone("+82 (2) 345-67-89");
            assertEquals("+82 (2) 345-67-89", manufacturer.getPhone());
        }
    }
    
    @Nested
    @DisplayName("Тесты метода toString")
    class ToStringTests {
        
        @Test
        @DisplayName("toString должен возвращать корректное строковое представление")
        void testToString() {
            Manufacturer m = new Manufacturer(1L, "ООО Тест", "Россия", "Иванов И.И.", "+7 (495) 123-45-67");
            
            String expected = "Manufacturer {id = 1, name = 'ООО Тест', country = 'Россия', contactPerson = 'Иванов И.И.', phone = '+7 (495) 123-45-67'}";
            assertEquals(expected, m.toString());
        }
    }
    
    @Nested
    @DisplayName("Тесты equals и hashCode")
    class EqualsHashCodeTests {
        
        @Test
        @DisplayName("Два объекта с одинаковыми id должны быть равны")
        void testEquals() {
            Manufacturer m1 = new Manufacturer(1L, "Тест1", "Россия", "Иванов", "123");
            Manufacturer m2 = new Manufacturer(1L, "Тест2", "США", "Петров", "456");
            
            assertEquals(m1, m2);
        }
        
        @Test
        @DisplayName("Два объекта с разными id не должны быть равны")
        void testNotEquals() {
            Manufacturer m1 = new Manufacturer(1L, "Тест", "Россия", "Иванов", "123");
            Manufacturer m2 = new Manufacturer(2L, "Тест", "Россия", "Иванов", "123");
            
            assertNotEquals(m1, m2);
        }
        
        @Test
        @DisplayName("hashCode должен быть одинаковым для равных объектов")
        void testHashCode() {
            Manufacturer m1 = new Manufacturer(1L, "Тест", "Россия", "Иванов", "123");
            Manufacturer m2 = new Manufacturer(1L, "Тест", "Россия", "Иванов", "123");
            
            assertEquals(m1.hashCode(), m2.hashCode());
        }
    }
}