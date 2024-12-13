# Тестовое задание
***
## Требования:
Создать приложение-утилиту, которая будет принимать файлы и сортировать данные в разные файлы.

Задача утилиты записать разные типы данных в разные файлы. Целые числа в один
выходной файл, вещественные в другой, строки в третий. По умолчанию файлы с
результатами располагаются в текущей папке с именами integers.txt, floats.txt, strings.txt.
По умолчанию файлы результатов перезаписываются. Так же файлы создаются только в случае необходимости и
при указании дополнительной опции возможно получение статистики.

#### Дополнительные опции:
* -o задаёт путь по которому будут сохранены файлы, требует после указания обязательный аргумент.
* -p позволяет добавить префикс к названию файлов, требует после указания обязательный аргумент.
* -a позволяет добавлять запись в существующие файлы.
* -s позволяет получить короткую статистику по отсортированным файлам.
* -f позволяет получить полную статистику по отсортированным файлам.
***
## Стэк приложения:
- Java 21
- Maven 3.8.1
- Lombok в качестве расширения функциональности кода
```
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
  </dependency>
```
- Логирование Slf4j
```
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.16</version>
  </dependency>
  
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.24.2</version>
  </dependency>
  
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.24.2</version>
  </dependency>
```
- Тестирование через JUnit и jMockit
```
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.11.3</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.jmockit</groupId>
    <artifactId>jmockit</artifactId>
    <version>1.49</version>
    <scope>test</scope>
  </dependency>
```
- Для отображения покрытия тестов использовался JaCoCo
```
  <dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
  </dependency>
```
***
## Инструкция к использованию утилиты
1. Собрать приложение с помощью команды Maven

```sh
package
```

2. Ввести в консоль команду которая начинается с

```sh
java -jar TestTask-1.0.0.jar 
```
после чего указать опции и файлы к прочтению.

#### Пример:
```sh
java -jar TestTask-1.0.0.jar -s -a input.txt
```
