# legacy-app
Code for the Improve your legacy app with Spring talks.

There are several branches showing the migration from a Legacy Monolitic application to a well (or better) structured application which can be broken into different parts.

[Library Upgrades](https://github.com/mdeinum/legacy-app/tree/library-upgrades)
---
Shows the upgrade from Spring 2.0.8 to the 3.2.13 version of Spring. The 3.2 version is the last version to include a `SimpleFormController` and this would allow for a easier migration path. There is now the choice to either use the `SimpleFormController` albeight deprecated, or to switch to `@Controller`.

The JDK has been updated from 1.4 to 1.6 and the code remains unchanged. 

[Testcase](https://github.com/mdeinum/legacy-app/tree/testcase)
---
Added an integration test for the `UserServiceImpl` to test the creation of the user, sending an email and synchronize with a remote system. With monolitic application services often do too much things and a unit test is in those case basically an integration test. 

Use mocking/stub libraries to mimic services like SMTP, FTP etc. 

[Separate Services](https://github.com/mdeinum/legacy-app/tree/services)
---
Seperate the sending mail en synchronizing to seperate services which the `UserServiceImpl` simply delegates to. There is also need for change of the configuration as we now need to have a couple more dependencies injected into the `UserServiceImpl`.

The testcase remains untouched and still should give green bars indicating everything is still working.

[Separate Services Packaging](https://github.com/mdeinum/legacy-app/tree/services-packages)
---
The different services and accompining classes have been moved into different functional packages like `nl.conspect.legacy.user` and `nl.conspect.legacy.mail`.

The testcase is moved to a different package, but for the remainder remains untouched.

[Events](https://github.com/mdeinum/legacy-app/tree/events)
---
Introduce events to have even more loosely coupled components.

[Independent Services](https://github.com/mdeinum/legacy-app/tree/independent-services)
---
Now that everything is nicely structured we have the ability to move those different parts to indepedent applications. Here we move the mail service to a seperate application and interface with it throuhg Spring Remoting, Spring Integration using JMS with and without XML marshalling.

MVC
===
A question arose during the webinar regarding the migration of older `SimpleFormController` implementations to `@Controller` and `@RequestMapping`. The first step is to write a testcase for the controller(s) you want to migrate then one by one migrate your controllers to `@Controller`. This way you have a controlled migration path. 

As soon as you migrated a full functional package to the new controllers you could think of moving it to an indepent service and use for instance Spring Boot to launch that part of your application.

**Note:** *This  means that during migration you are bound to the 2.x or 3.x versions of Spring as that includes the `SimpleFormController`, Spring 4.x and later don't contain that class anymore.*

- [Testcase MVC](https://github.com/mdeinum/legacy-app/tree/testcase-mvc)
- [Controller](https://github.com/mdeinum/legacy-app/tree/testc)
