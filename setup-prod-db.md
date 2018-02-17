# RDS Database Setup

Created a dev micro instance in the AWS console

connected via a server already in the VPC so that the database isn't publicly accessible

Connect to the database

`mysql -u <db-username> -p -h <db-hostname>`

Change databases to recipe

`use recipe;`

Load the data by copying and pasting the database export from my local machine

Create the database user

`create user recipe_user identified by '<password>';`

Grant permissions for the recipe_user to access the recipe database

grant select on recipe.* to 'recipe_user';
grant update on recipe.* to 'recipe_user';
grant delete on recipe.* to 'recipe_user';
grant insert on recipe.* to 'recipe_user';


