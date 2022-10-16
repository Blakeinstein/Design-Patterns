# PTBS

## Design Patterns

### Required Patterns

#### 1. Facade

- Implemented: 
  
  > 1. AppView class, `src/view/AppView.java` Controls the entire application gui, and behaves as entry point.
  > 
  > 2. Facade class, `src/controller/Facade.java` Controls the entire application behaviour.

- Called:
  
  > 1. Main -> `new AppView()`
  > 
  > 2. AppView -> `new Facade()`

#### 2. Bridge

- Implemented:
  
  > 1. Person.CreateProductMenu, `src/models/Person.java:77` abstract function for
  >    
  >    1. Buyer.CreateProductMenu, `src/models/Buyer.java:28`
  >    
  >    2. Seller.CreateProductMenu, `src/models/Seller.java:28`
  > 
  > 2. Both of the above serve as bridges to the respective create product menu as requested by the user.

- Called:
  
  > Facade.productOperation, `src/controller/Facade.java:358` which serves as the bridging call, calling the respective create product menu functions depending on the logged in user.

#### 3. Factory

- Implemented:
  
  > 1. Person.CreateProductMenu, `src/models/Person.java:77` abstract function for
  >    
  >    1. Buyer.CreateProductMenu, `src/models/Buyer.java:28`
  >    
  >    2. Seller.CreateProductMenu, `src/models/Seller.java:28`
  > 
  > 2. Both of the above implement a factory method for their own product menu, which creates the appropriate product menu by invoking the constructor for NewProductMenu class, `src/view/NewProductMenu.java`

- Called:
  
  > 1. NewProductMenu.CreateProductDialog, `src/view/NewProductMenu.java:68`

#### 4. Iterator

- Implemented:
  
  > ListIterator class, `src/util/ListIterator.java` serves as an interface class for all iterator models below
  > 
  > 1. ProductIterator class, `src/util/ProductIterator.java`, iterator model for ClassProductList class `src/models/ClassProductList.java`.
  > 
  > 2. OfferingIterator class, `src/util/OfferingIterator.java`, iterator model for OfferingList class `src/models/OfferlingList.java`.

- Called:
  
  > 1. Facade class,
  >    
  >    1. To check for if newly added product already exists. `src/controller/Facade.java:360`
  >    
  >    2. To set the currently selected product `src/controller/Facade.java:340`
  >    
  >    3. To submit marked offerings, `src/controller/Facade.java:214`
  > 
  > 2. ClassProductList class, `src/models/ClassProductList.java:18` to pass the visitor to the products inside.
  > 
  > 3. In multiple views to iterate over the offerings and products.

#### 5. Visitor

- Implemented:
  
  > NodeVisitor class, `src/controller/NodeVisitor.java:` serves as the abstract class for the ReminderVisitor class `src/controller/ReminderVisitor`.

- Called:
  
  > Facade.remind, `src/controller/Facade.java:240`, creates a reminder object which is passed to the remindervisitor. The visitor visits the facade `src/controller/Facade.java:243`, then is passed down to its product list.`src/controller/Facade.java:415` The product list passes the visitor to the individual products `src/models/ClassProductList.java:18` and the products pass the visitor to its own tradings `src/models/Product.java:88`. When visiting the tradings, the visitor updates overdue and pending tradings for the reminder object `src/controller/ReminderVisitor.java:35`.

### Extra Patterns

#### 1. Singleton

- Implemented:
  
  > 1. AppView class, `src/view/AppView.java`
  > 
  > 2. Login class, `src/controller/Login.java`

- Called:
  
  > 1. Main.main, `src/Main.java:8` serves as the program entrypoint. The singleton for appview exists, to be able to serve as the root window for all subsequent dialogs and popups, as a subchild to the main window.
  > 
  > 2. The singleton for Login exists, such that the app can access registered users from anywhere within the app, without reading the db on disk again and again, and instead directly requesting information from the internal db of the login class.

## Running the project

> ***The project was made using intellij, and should only be compiled via intellij.***



### Via Intellij

1. Open the project in intellij

2. Check that the run configuration is `PTBS` and click run. (or press CTRL + F5)

### Via precompiled binary

1. A binary exists as `artifacts/bin/Binary.jar` that can be used to execute the project skipping the compilation step. Minimum `JDK 16`.


