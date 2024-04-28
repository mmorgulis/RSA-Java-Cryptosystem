# RSA JAVA IMPLEMENTATION
A java simple implentation of RSA cryptosystem with java class java.math.BigInteger

## Principal class
##### Keys  
Class for generating keys 
Constructors:
``` java
Keys key = new Keys(); // new random keys
Keys key = new Keys(BigInteger N, BigInteger r, BigInteger p, BigInteger q); // preset keys
```

## Principal Methods 
#### Static
``` java
Division.bezoutIdentity(BigInteger num1, BigInteger num2); // find the bezout identity between num1 and num2
SquareAndMultiply.modularExponentation(BigInteger a, BigInteger n, BigInteger m); // calculate a^n mod m efficiently
```
#### Non-Static
``` java
key.generateKeys(); // create new rsa keys 
key.encrypt(String message); // encrypt message using r coefficient of the public key and return the message encrypted
key.decrypt(BigInteger [] messageEncrypted); // decrypt message using private key

```