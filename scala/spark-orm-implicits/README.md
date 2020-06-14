### Унифицированный механизм запросов в Spark 

На текущий момент работа таблицами hive выполняется методом copy-pasta шаблонного кода

Пример такого кода

```

 val query = s"""
           |SELECT
           |  person_index AS index, 
           |  person_name AS name
           |  person_last_name AS lname
           |  person_birth_date AS bd
           |  ... -- other fields
           |FROM $schema.person_table_name

// Дальше примерно такой шаблонный код
def asPerson(w: Row): Person = {
    Person(
      w.getAs[Long]("person_index"),
      Option(w.getAs[String]("person_name")),
      Option(w.getAs[String]("person_last_name")),
      w.getAs[String]("person_birth_date"),
      ...)
    )
    
val result = sparkSession.sql(query).map(asPerson)
``` 

В этом проекте предлагаю проанализировать возможность создания 
- обобщенного механизма работы с таблицами в spark, 
- type-safe конвертация spark.sql.Row => MyClass
- и т.д. "вкусные" функции

Возможно, в мире spark-sql эта проблема уже решена более элегантным способом, если так, то стоит позаимствовать подходы\библиотеки 

Классно было бы, если код работы с таблицами выглядел примерно так

```
    case class Person(name: String, lname: String, age: Int, address: Option[String])
    case class Address(name: String, country: String)
    
    import my_spark_sql.implicits._
    val dataset[Person] = sparkSql.select[Person](s"$my_table_name").get

    // или другой способ, если нужны join'ы
    
    val dataset[(Person, Option[Addreess])] = sparkSql
         .select[Person]("$my_table_name")
         .join[Address]("$my_second_table_name")
         .on(Person.address, Address.name)
         .get
         
 ```
