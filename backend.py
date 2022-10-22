import re
from unittest import result
from flask import Flask, jsonify
import json
import sqlite3

connection = sqlite3.connect('chats.db',check_same_thread=False)
cursor = connection.cursor()
# cursor.execute("DROP TABLE IF EXISTS user")

# table = """ CREATE TABLE user (
#             username VARCHAR(255) NOT NULL,
#             token VARCHAR(355) NOT NULL
#         ); """

# cursor.execute(table) 

# table2=""" CREATE TABLE status(
#     username VARCHAR(255) NOT NULL,
#     online int,
#     selected_user VARCHAR(255) NOT NULL
# )
# """
# cursor.execute(table2)

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello World'

@app.route('/register/<uname>/<token>')
def reg(uname,token):
    cursor.execute(f"INSERT INTO user VALUES ('{uname}','{token}')")
    cursor.execute(f"INSERT INTO status VALUES ('{uname}',0,'none')")
    connection.commit()
    return 'done'

@app.route('/show/<uname>')
def show(uname):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"SELECT token FROM user WHERE username='{uname}'")
            b=cursor.fetchall()
            print(b[0][0])
            # var=json.loads(b)
            return b[0][0]#jsonify(b)[0]
     
    return "username not found"
        

@app.route('/delete')
def delete():
    cursor.execute(f"DELETE FROM user")
    return "deleted"

@app.route('/update/<uname>/<token>')
def update(uname,token):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"UPDATE user SET token='{token}' WHERE username='{uname}'")
            connection.commit()
            return "updated"
    return "uname not found"
        
@app.route('/status/<uname>/<bit>')
def status(uname,bit):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"UPDATE status SET online={bit} WHERE username='{uname}'")
            return "updated"
    return "username not found"

@app.route('/setSelected/<uname>/<user>')
def change(uname,user):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"UPDATE status SET selected_user='{user}' WHERE username='{uname}'")
            return "updated"
    return "username not found"

@app.route('/getSelectedUser/<uname>')
def show_selectedUser(uname):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"SELECT selected_user FROM status where username='{uname}'")
            result=cursor.fetchall()
            print(result[0][0])
            return result[0][0]
    return "username not found"

@app.route('/getOnlineStatus/<uname>')
def show_status(uname):
    for i in cursor.execute(f"SELECT username FROM user").fetchall():
        if i[0]==uname:
            cursor.execute(f"SELECT online FROM status where username='{uname}'")
            result=cursor.fetchall()
            print(result[0][0])
            return str(result[0][0])
    return "username not found"



@app.route('/all_status')
def all():
    b=[] 
    cursor.execute("SELECT * FROM status")
    print(cursor.fetchall())
    a=cursor.fetchall()
    for i in a:
        b.append(i[0])
        print(i)
    return str(b)


@app.route('/all')
def al():
    b=[] 
    cursor.execute("SELECT * FROM user")
    print(cursor.fetchall())
    a=cursor.fetchall()
    for i in a:
        b.append(i[0])
        print(i[0])
    return str(b)

if __name__ == '__main__':
    app.run(port=5000)
