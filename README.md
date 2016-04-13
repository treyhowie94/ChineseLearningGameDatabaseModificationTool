# ChineseLearningGameDatabaseModificationTool
Repo for the database modifier tool

This repo contains the source code (undocumented) for the modifier tool for the Chinese Learning Game. This tool allows for adding new characters, their pinyin value, definition, and grade/experience level. The user can also search for english words or chinese characters and edit their information as needed, as well as delete the entry from the database. Finally, there are advanced options that allow for the deletion and restoration of the database, if such tasks are needed. Restoration cannot be reversed and deletion can only be reversed to the degree of the original database (i.e. all additional characters added to the database will be lost).

The classes for the login and database connection are both removed from this repo because i'm lazy and those files contain sensitive information and I don't want to make web accessible files for password key pairs. If you need thoses classes for whatever reason, ask me directly.
