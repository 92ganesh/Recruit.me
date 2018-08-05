import psycopg2


def selectData(tableName):
    command = "SELECT * FROM "+tableName
    print(command)
    executeQuery(command)
 
def insertData(tableName,listOfValues):
    ''' to insert data in the PostgreSQL database'''
    
    # form the insert statement
    command = "INSERT INTO "+tableName+" VALUES("
    for i in listOfValues:
        if(type(i) is int):
            command=command+str(i)+","
        elif(type(i) is str):
            command=command+"'"+i+"',"

    command=command[:-1]
    command=command+")"
    
    # print the created command
    print(command)

    # execute the command
    executeQuery(command)

    print("Data inserted\n")



def executeQuery(queryString):
    '''ref: http://www.postgresqltutorial.com/postgresql-python/'''

    conn = None
    try:
        # following line include some data that needs to be hard coded
        conn = psycopg2.connect(host="localhost",database="pythonDB", user="postgres", password="postgres")
        
        cur = conn.cursor()
        cur.execute(queryString)
       
        if (queryString[0:6]=='SELECT'):
            result = cur.fetchall()
            for i in result:
                print(i)
            print("")
        
        # close communication with the PostgreSQL database server
        cur.close()
        
        # commit the changes
        conn.commit()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()
 
 

# MAIN starts here
command="create table TVseries(ven_id integer PRIMARY KEY,vendor_name varchar)"
executeQuery(command)
insertData("TVseries",[1,"Friends"])
insertData("TVseries",[2,"Silicon Valley"])
insertData("TVseries",[3,"Dare Devil"])
selectData('TVseries')