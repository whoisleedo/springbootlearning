## 1. Data Schema 

### table name: account

| column        | restrict         |           meaning  |
|:-------------:|:-------------:   |  :-------------:   |
| id   |  int pk  ai | db id  |
| user_account   | varchar(15) uk not null |   使用者帳號|
| password       | varchar(255) not null   |加密後的密碼|
| name        | varchar(30) not null   |使用者名稱      |
| email      | 	varchar(255)  | 使用者信箱              |




## 2. Object Design

### Name:	CommonResponse

| name        | type               |     nullable       |
|:-------------:|:-------------:   |  :-------------:   |
| status             |  Integer    | 	false           |
| errorMessage       | String      |   	false|
| body               | T           |true|



### Name:	AccessTokenDto

| name        | type               |     nullable       |
|:-------------:|:-------------:   |  :-------------:   |
| accessToken             |  String    | 	false           |




### Name:	AccountDto

| name          | type              |     nullable      |
|:-------------:|:-------------:   |  :-------------:   |
| account       |  String          | 	false,  length 5 -15  |
| password      | String           |   	false,  null length 5 - 30   |
| name          | String           |false,  null length 1 - 30     |
| email         | String           |true ,	match email REGEX|


### Name:	LoginDto

| name          | type              |     nullable      |
|:-------------:|:-------------:   |  :-------------:   |
| account       |  String          | 	false,  length 5 -15  |
| password      | String           |   	false,  null length 5 - 30   |




### Name:	ResetPasswordDto

| name          | type              |     nullable      |
|:-------------:|:-------------:   |  :-------------:   |
| currentPassword       |  String     | 	false,  null length 5 - 30  |
| newPassword      | String           |   	false,  null length 5 - 30  |


### Name:	UserDto

| name          | type              |     nullable      |
|:-------------:|:-------------:   |  :-------------:   |
| id            |  int             | 	false                    |
| account       | String           |   	false, length 5 -15      |
| name          | String           |false,  null length 1 - 30   |
| email         | String           |true ,	match email REGEX    |

## Common Response Status value
| status code   | mean             |  
| :-------------: |:-------------: |  
| 1         | success              |  
| -1        | input invalid data   |
| -2        | exception situation  |
| -3        | account unavailable  |
| -4        | access token invalid |
| -5        | user not found       |

## Jwt Token spec
|      property      |       mean        |  
|:------------------:|:-----------------:|  
|      subject       |      account      |  
|       issuer       |     	demoApi      |
| preferred_username |      account      |
|       email        |       email       |
|         id         |       db_id       |
|        name        |     user_name     |
|      roles      | List of user_role |



#  3. apis
## register api

| property        | mean           |  
| :-------------: |:-------------: |  
| path            | /api/sessions  |  
| method          | 	HTTP.POST  |
| request body    | AccountDto |
| response        | CommonResponse with null body  |


## getUser api

| property        | mean             |  
| :-------------: |:-------------:   |  
| path            | /api/users/{id}  |  
| header          | 	Authorization	Bearer {accessToken}     |  
| method          | 		HTTP.GET       |
| response        | CommonResponse with UserDto as Body member   |


## login api

| property        | mean             |  
| :-------------: |:-------------:   |  
| path            | /api/sessions    |  
| method          | 		HTTP.POST|
| request body    | LoginDto         |
| response        | 	CommonResponse with AccessTokenDto as Body member|


## reset password api

| property        | mean             |  
| :-------------: |:-------------:   |  
| path            |     /api/users/{id}/password                 |  
| header          | 	Authorization	Bearer {accessToken}     |  
| method          | 		HTTP.PUT                             |
| request body    | ResetPasswordDto                             |
| response        | 	CommonResponse with null body            |
