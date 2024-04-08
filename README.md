Basic implementation of technical task,
MVVM presentation layer with multi-module Clean architecture [app, presentation,domain, data modules] 

Presentation: view binding (viewbinding flavor), single activity, MVVM, Material design components

Domain: no business logic indeed, that's why no interactors/usecases/facades,
only picture repository interface, domain entities (include exceptions).

Data: picture repository implementation + 2 data sources, remote(Retrofit) and local(Room)

App: Hilt, Espresso tests

If it needs to develop an application further: 
1 - normalize database, it needs to create extra table for tags and tie it to pictures table;
2 - create domain entity for data response, add Call adapter for Retrofit;
3 - refactor presentation layer, abstraction for base entities (fragment, viewmodel) 
4 - possibly to create another flavor for compose implementation
5 - add more UI and unit tests