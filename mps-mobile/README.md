# Spring MVC Form Validation with Annotations Tutorial
- http://codetutr.com/2013/05/28/spring-mvc-form-validation/
- Error messages are resolved using the following pattern:

- {ValidationClass}.{modelObjectName}.{field}
- For example, if the age field of our “subscriber” model object fails the “NotNull” validation, the “NotNull.subscriber.age” message would be looked up. If the message isn’t found, “NotNull.subscriber” would be looked for. Finally, if not found, “NotNull” message would be looked for. If that also isn’t found, the default message (what we saw above) would be rendered. With this convention in mind, let’s define our error messages:

- Size=the {0} field must be between {2} and {1} characters long
- Size.subscriber.name=Name must be between {2} and {1} characters
- Size.subscriber.phone=Phone must be at least {2} characters
 
- Min.subscriber.age=You must be older than {1}
- Max.subscriber.age= Sorry, you have to be younger than {1}
 
- Email=Email address not valid
- Past=Date must be in the past
 
- NotEmpty=Field cannot be left blank
- NotNull=Field cannot be left blank
 
- typeMismatch=Invalid format
- methodInvocation.myRequest.amount=Invalid format
- Notice the use of {0}, {1}, etc. These are arguments that can be passed in to the message.
