# File-Encryptor-and-Decryptor
Warning: The File-Encryptor-and-Decryptor program provided is for educational and entertainment purposes only. It is not intended for use with important or sensitive files. Use of this program is at your own risk, and I am not responsible for any misuse, damage, or loss of data that may occur as a result of using this program.

Encryption Warning: Please note that encrypted files can be difficult or impossible to recover if you lose the encryption key. It is important to keep a backup of the key in a secure location.

Decryption Warning: If you enter the wrong key or initialization vector when decrypting a file, the decrypted file may be corrupted or unusable. Please make sure you have the correct key and initialization vector before attempting to decrypt any files.

Security Warning: Encrypting files does not guarantee their security. Encrypted files can still be vulnerable to theft or hacking if they are not properly secured. Please take appropriate measures to protect your encrypted files and keep the encryption key secure.

User Input Warning: When entering the key and initialization vector manually, please ensure that you enter them correctly. Any mistakes may lead to the loss or corruption of your files. We recommend copying and pasting the values from a secure location to avoid errors.

General Warning: This encryptor should only be run in a separate folder. Please create a new folder and drop the files to be encrypted. Do not encrypt important files with this program. If you want to encrypt important data, please use a dedicated software.

The encryptor uses the Advanced Encryption Standard (AES) algorithm with a 128-bit key and Cipher Block Chaining (CBC) mode of operation. CBC mode uses an initialization vector (IV) that is randomly generated and combined with the key to encrypt the first block of data. 

The decryptor uses the same AES algorithm and CBC mode of operation to decrypt the encrypted files. If the program finds the key.txt file in the same folder as the encrypted files, it automatically reads the key and IV from the file and uses them to decrypt the files. Otherwise, the user is prompted to enter the key and IV manually in string format (16 characters each). 

It is important to note that while AES is considered a strong encryption algorithm, the security of the encrypted files ultimately depends on the strength of the key and IV. It is recommended to use a strong and unique key and IV for each file to ensure maximum security. This program uses a random 16 byte key and 16 byte initialization vector, but it does not change the key for every file.
