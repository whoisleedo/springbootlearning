# springbootlearning

## 1.Data Schema

table name: account    
column:   id int pk    
user_account 	varchar(15) uk not null   
password		varchar(255) not null   Bcrypt encoded   
name			varchar(30) not null   
email			varchar(255)



## 2.Object Design

Name :	CommonResponse   
Properties:  
Integer  	status  			not null   
String      errorMessage 		not null   
T	        body     


Name:	AccessTokenDto   
Properties:   
String       accessToken		not null   


Name:	AccountDto   
Properties:
String 	account			not null length 5 -15   
String	password			not null length 5 - 30   
String	name				not null length 1 - 30     
String 	email				match email REGEX   

Name:	LoginDto   
Properties:   
String 	account			not null length 5 -15   
String	password		not null length 5 - 30      


Name:	ResetPasswordDto   
Properties:   
String 	currentPassword	not null length 5 - 30   
String	newPassword		not null length 5 - 30   

Name:	UserDto   
Properties:	    
int		id				not null   
String 	account			not null length 5 -15   
String	name				not null length 1 - 30   
String 	email				REGEX   

Common Response Status value   

1   -  success   
-1	- input invalid data   
-2	- exception situation  
-3	- account unavailable   
-4	- access token invalid   


#  3.apis
## register api   
path:	/api/sessions   
method:		HTTP.POST   
request body:	AccountDto   
response: 		CommonResponse with null body   

## getUser api
path:			/api/users/{id}      
header: 		Authorization	Bearer {accessToken}   
method:		HTTP.GET   
response: 		CommonResponse with UserDto as Body member   

## login api
path:			/api/sessions   
method:		HTTP.POST   
request body:	LoginDto   
response: 		CommonResponse with AccessTokenDto as Body member      
   

## reset password api
path:			/api/users/{id}/password   
header: 		Authorization	Bearer {accessToken}   
method:		HTTP.PUT   
request body:	ResetPasswordDto   
response: 		CommonResponse with null body   
