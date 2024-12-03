# java-mvc-dao-crud
This is a project built with Java language, using MVC architecture and DAO (Data Access Object).

MVC is a very popular architecture to separate code between different layers (Model, View, and Controller)

Inside model layer, we can define different classes. In this case, we've made a project simulating a library. `Libro.cs` is the class that defines Libro inside the program. Also, DAO files can be found inside model. In addition, we can store the logic for database connection and also some extra classes (like `ValidacionException.cs`).

On the other hand, controller layer works like a "bridge" between the model and view. In this layer, we can find all the logic associated with validations, methods, events, etc.

Finally, the view contains all the design-related. In this layer we can find things like: styling, elements such as buttons, tables, labels.

In order to add more context, DAO usually contains all the actions for CRUDs, and this project is a very good example to understand this part. Inside DAO class, we can find SQL scripts to select data from database, inserts, updates, and deletions.

# Usage
You need:

- Install Oracle 19c (write down all credentials)
- Install SQL Developer, log in with system user and create a new user with all permissions
- Download `ojdbc8.jar` and install into librería project
- Once you're ready with SQL Developer, execute the script inside `script.sql` file in the root directory of this repository