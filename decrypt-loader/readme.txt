ClassForEncryption.java and ClassForEncryption.class is needed for the encryption/decryption. 
It is better to save them to a secret place as it is mentioned below.

1. mvn clean install -Dtask=build
	This creates $WORKSPACE\decrypt-loader\target\classes\secret_project\ClassForEncryption.class if ClassForEncryption.java is available.
	If java file not exists, copy the previously saved ClassForEncryption.java to decrypt-loader\src\main\java\secret_project, then redo this step.

2. copy ClassForEncryption.class file into e.g. D:\
3. (after saving them to a secret place) delete them from the project:
	secret_project\ClassForEncryption.java
	secret_project\ClassForEncryption.class

4. mvn clean install -Dtask=encrypt -DencryptionKey=XYZ -DfilePath=D:\ClassForEncryption.class
	Creates D:\ClassForEncryption.class.encrypted with the encryptionKey.

7. mvn clean install -Dtask=decrypt -DencryptionKey=XYZ -DfilePath=D:\ClassForEncryption.class.encrypted
	Decryptes D:\ClassForEncryption.class.encrypted with the encryptionKey.

8. mvn clean install -Dtask=decrypt -DencryptionKey=invalidkey -DfilePath=D:\ClassForEncryption.class.encrypted
	By using an incorrect encryption key, the program stops.