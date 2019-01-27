# MultiplicationService

A multitier architecture will provide our application with a more production-ready look. Most of the real-world applications follow this architecture pattern and, to be more specific, the three-tier design is the most popular one and widely extended among web applications. The three tiers are:

Client tier: Responsible for the user interface. Typically what we call the frontend.

Application tier: It contains all the business logic together with the interfaces to interact with it and the data interfaces for persistence. This maps with what we call the backend.

Data Store tier: It’s the database, file system, etc., that persists the application’s data.

In this book we’re mainly focused on the application tier, although we’ll use the other two as well. If now we zoom in, that application tier is commonly designed using three layers:

Business layer: The classes that model our domain and the business specifics. It’s where the intelligence of the application resides. Normally, it will be composed of entities (our Multiplication) and Services providing business logic (like our MultiplicationService). Sometimes this layer is divided in two parts: domains (entities) and applications (services).

Presentation layer: In our case, it will be represented by the Controller classes, which will provide functionality to the Web client. Our REST API implementation will reside here.

Data layer: It will be responsible for persisting our entities in a data storage, usually a database. It can typically include Data Access Object (DAO) classes, which work with direct representation of the database model, or Repository classes, which are domain-centric and translate from domains down to the database layer (so they could use DAOs whenever they don’t match).



