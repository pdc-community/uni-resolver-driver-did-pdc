![DIF Logo](https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png)

# Universal Resolver Driver: did:pdc

This is a [Universal Resolver](https://github.com/decentralized-identity/universal-resolver/) driver for  **did:pdc** 
identifiers.

## Specifications

* [Decentralized Identifiers](https://www.w3.org/TR/did-core/)
* [DID Method Specification](https://danubetech.github.io/did-method-dns/)

## Example DIDs

```
did:pdc:8801:0xf47b66bc0d9b7c73f9ff27bf1f49a2b69dc167fc
```
## Build and Run (Docker)

```
docker build -f ./docker/Dockerfile . -t w744219971/driver-did-pdc
docker run -p 8080:8080 w744219971/driver-did-pdc
curl -X GET http://localhost:8080/1.0/identifiers/did:pdc:8801:0xf47b66bc0d9b7c73f9ff27bf1f49a2b69dc167fc
```

## Build (native Java)

Maven build:

	mvn clean install
