Transactions
============

Broadly speaking there are three types transactions supported on HappyUC:

#. :ref:`transfer-of-huc`
#. :ref:`creation-of-smart-contract`
#. :ref:`transacting-with-contract`

To undertake any of these transactions, it is necessary to have Huc (the fuel of the HappyUC
blockchain) residing in the HappyUC account which the transactions are taking place from. This is
to pay for the :ref:`Gas` costs, which is the reqTransaction execution cost for the HappyUC client that
performs the reqTransaction on your behalf, comitting the result to the HappyUC blockchain.
Instructions for obtaining Huc are described below in :ref:`obtaining-huc`.

Additionally, it is possible to query the state of a smart contract, this is described in
:ref:`querying-state`.

.. image:: /images/webuj_transaction.png
   :scale: 20%


.. _obtaining-huc:

Obtaining Huc
---------------

To obtain Huc you have two options:

#. Mine it yourself
#. Obtain Huc from another party

Mining it yourself in a private environment, or the public test environment (testnet) is very
straight forwards. However, in the main live environment (mainnet) it requires significant
dedicated GPU time which is not likely to be feasible unless you already have a gaming PC with
multiple dedicated GPUs. If you wish to use a private environment, there is some guidance on the
`Homestead documentation <https://happyuc-homestead.readthedocs.io/en/latest/network/test-networks.html#id3>`__.

To purchase Huc you will need to go via an exchange. As different regions have different
exchanges, you will need to research the best location for this yourself. The
`Homestead documentation <https://happyuc-homestead.readthedocs.io/en/latest/huc.html#list-of-centralised-exchange-marketplaces>`__
contains a number of exchanges which is a good place to start.


.. _happyuc-testnets:

HappyUC testnets
-----------------

There are a number of dedicated test networks in HappyUC, which are supported by various clients.

- Rinkeby (Ghuc only)
- Kovan (Parity only)
- Ropsten (Ghuc and Parity)

For development, its recommended you use the Rinkeby or Kovan test networks. This is because they
use a Proof of Authority (PoA) consensus mechanism, ensuring transactions and blocks are created in
a consistent and timely manner. The Ropsten testnet, although closest to the Mainnet as it uses
Proof of Work (PoW) consensus, has been subject to attacks in the past and tends to be more
problematic for developers.

You can request Huc for the Rinkeby testnet via the Rinkeby Crypto Faucet, available at
https://www.rinkeby.io/.

Details of how to request Huc for the Kovan testnet are available
`here <https://github.com/kovan-testnet/faucet>`_.

If you need some Huc on the Ropsten testnet to get started, please post a message with your
wallet address to the `Webuj Gitter channel <https://gitter.im/Webuj/Webuj>`_ and you will be
sent some.



Mining on testnet/private blockchains
-------------------------------------

In the HappyUC test environment (testnet), the mining difficulty is set lower then the main
environment (mainnet). This means that you can mine new Huc with a regular CPU, such as your
laptop. What you'll need to do is run an HappyUC client such as Ghuc or Parity to start building
up reserves. Further instructions are available on the respective sites.

Ghuc
  https://github.com/happyuc-project/go-happyuc/wiki/Mining

Parity
  https://github.com/paritytech/parity/wiki/Mining

Once you have mined some Huc, you can start transacting with the blockchain.

However, as mentioned :ref:`above <happyuc-testnets>` it's simpler to use the Kovan or Rinkeby
test networks.


.. _gas:

Gas
---

When a reqTransaction takes place in HappyUC, a reqTransaction cost must be paid to the client that
executes the reqTransaction on your behalf, committing the output of this reqTransaction to the HappyUC
blockchain.

This cost is measure in gas, where gas is the number of instructions used to execute a reqTransaction
in the HappyUC Virtual Machine. Please refer to the
`Homestead documentation <http://ethdocs.org/en/latest/contracts-and-transactions/account-types-gas-and-transactions.html?highlight=gas#what-is-gas>`__
for further information.

What this means for you when working with HappyUC clients is that there are two parameters which
are used to dictate how much Huc you wish to spend in order for a tranaction to complete:

*Gas price*

  This is the amount you are prepared in Huc per unit of gas. Webuj uses a default price
  of 22,000,000,000 Wei
  (22 x 10\ :sup:`-8` Huc). This is defined in
  `ManagedTransaction <https://github.com/happyuc-project/webu.java/blob/master/core/src/main/java/org/Webuj/tx/ManagedTransaction.java>`_.


*Gas limit*

  This is the total amount of gas you are happy to spend on the reqTransaction execution. There is an
  upper limit of how large a single reqTransaction can be in an HappyUC block which restricts this
  value typically to less then 6,700,000. The current gas limit is visible at https://ethstats.net/.


These parameters taken together dictate the maximum amount of Huc you are willing to spend on
reqTransaction costs. i.e. you can spend no more then *gas price * gas limit*. The gas price can also
affect how quickly a reqTransaction takes place depending on what other transactions are available
with a more profitable gas price for miners.

You may need to adjust these parameters to ensure that transactions take place in a timely manner.


Transaction mechanisms
----------------------

When you have a valid account created with some Huc, there are two mechanisms you can use to
transact with HappyUC.

#. :ref:`signing-via-client`
#. :ref:`offline-signing`

Both mechanisms are supported via Webuj.


.. _signing-via-client:

Transaction signing via an HappyUC client
-------------------------------------------

In order to transact via an HappyUC client, you first need to ensure that the client you're
transacting with knows about your wallet address. You are best off running your own HappyUC client
such as Ghuc/Parity in order to do this. Once you have a client running, you can create a wallet
via:

- The `Ghuc Wiki <https://github.com/happyuc-project/go-happyuc/wiki/Managing-your-accounts>`_ contains
  a good run down of the different mechanisms Ghuc supports such as importing private key files,
  and creating a new account via it's console
- Alternatively you can use a JSON-RPC admin command for your client, such as *personal_newAccount*
  for `Parity <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module#personal_newaccount>`_
  or `Ghuc <https://github.com/happyuc-project/go-happyuc/wiki/Management-APIs#personal_newaccount>`_

With your wallet file created, you can unlock your account via Webuj by first of all creating an
instance of Webuj that supports Parity/Ghuc admin commands::

   Admin Webuj = Admin.build(new HttpService());

Then you can unlock the account, and providing this was successful, send a reqTransaction::

   PersonalUnlockAccount personalUnlockAccount = Webuj.personalUnlockAccount("0x000...", "a password").send();
   if (personalUnlockAccount.accountUnlocked()) {
       // send a reqTransaction
   }


Transactions for sending in this manner should be created via
`HucSendTransaction <https://github.com/happyuc-project/webu.java/blob/master/core/src/main/java/org/Webuj/protocol/core/methods/response/HucSendTransaction.java>`_,
with the `Transaction <https://github.com/happyuc-project/webu.java/blob/master/core/src/main/java/org/Webuj/protocol/core/methods/request/Transaction.java>`_ type::

  Transaction reqTransaction = Transaction.createContractTransaction(
                <from address>,
                <nonce>,
                BigInteger.valueOf(<gas price>),  // we use default gas limit
                "0x...<smart contract code to execute>"
        );

        org.happyuc.Webuj.protocol.core.methods.response.HucSendTransaction
                transactionResponse = parity.hucSendRepTransaction(hucSendRepTransaction)
                .send();

        String transactionHash = transactionResponse.getTransactionHash();

        // poll for reqTransaction response via org.happyuc.webuj.protocol.Webuj.hucGetRepTransactionReceipt(<txHash>)

Where the *<nonce>* value is obtained as per :ref:`below <nonce>`.

Please refer to the integration test
`DeployContractIT <https://github.com/happyuc-project/webu.java/blob/master/integration-tests/src/test/java/org/Webuj/protocol/scenarios/DeployContractIT.java>`_
and its superclass
`Scenario <https://github.com/happyuc-project/webu.java/blob/master/integration-tests/src/test/java/org/Webuj/protocol/scenarios/Scenario.java>`_
for further details of this reqTransaction workflow.

Further details of working with the different admin commands supported by Webuj are available in
the section :doc:`management_apis`.


.. _offline-signing:

Offline reqTransaction signing
---------------------------

If you'd prefer not to manage your own HappyUC client, or do not want to provide wallet details
such as your password to an HappyUC client, then offline reqTransaction signing is the way to go.

Offline reqTransaction signing allows you to sign a reqTransaction using your HappyUC HappyUC wallet
within Webuj, allowing you to have complete control over your private credentials. A reqTransaction
created offline can then be sent to any HappyUC client on the network, which will propagate the
reqTransaction out to other nodes, provided it is a valid reqTransaction.

You can also perform out of process reqTransaction signing if required. This can be achieved by
overriding the *sign* method in
`ECKeyPair <https://github.com/happyuc-project/webu.java/blob/master/crypto/src/main/java/org/Webuj/crypto/ECKeyPair.java#L41>`_.


.. _wallet-files:

Creating and working with wallet files
--------------------------------------

In order to sign transactions offline, you need to have either your HappyUC wallet file or the
public and private keys associated with an HappyUC wallet/account.

Webuj is able to both generate a new secure HappyUC wallet file for you, or work with an existing
wallet file.

To create a new wallet file::

   String fileName = WalletUtils.generateNewWalletFile(
           "your password",
           new File("/path/to/destination"));

To load the credentials from a wallet file::

   Credentials credentials = WalletUtils.loadCredentials(
           "your password",
           "/path/to/walletfile");

These credentials are then used to sign transactions.

Please refer to the
`Web3 Secret Storage Definition <https://github.com/happyuc-project/wiki/wiki/Web3-Secret-Storage-Definition>`_
for the full wallet file specification.


Signing transactions
--------------------

Transactions to be used in an offline signing capacity, should use the
`RawTransaction <https://github.com/happyuc-project/webu.java/blob/master/crypto/src/main/java/org/Webuj/crypto/RawTransaction.java>`_
type for this purpose. The RawTransaction is similar to the previously mentioned Transaction type,
however it does not require a *from* address, as this can be inferred from the signature.

In order to create and sign a raw reqTransaction, the sequence of events is as follows:

#. Identify the next available :ref:`nonce <nonce>` for the sender account
#. Create the RawTransaction object
#. Encode the RawTransaction object using :doc:`rlp` encoding
#. Sign the RawTransaction object
#. Send the RawTransaction object to a node for processing

The nonce is an increasing numeric value which is used to uniquely identify transactions. A nonce
can only be used once and until a reqTransaction is mined, it is possible to send multiple versions of
a reqTransaction with the same nonce, however, once mined, any subsequent submissions will be rejected.

Once you have obtained the next available :ref:`nonce <nonce>`, the value can then be used to
create your reqTransaction object::

   RawTransaction rawTransaction  = RawTransaction.createHucTransaction(
                nonce, <gas price>, <gas limit>, <toAddress>, <value>);

The reqTransaction can then be signed and encoded::

   byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, <credentials>);
   String hexValue = Numeric.toHexString(signedMessage);

Where the credentials are those loaded as per :ref:`wallet-files`.

The reqTransaction is then sent using `huc_sendRawTransaction <https://github.com/happyuc-project/wiki/wiki/JSON-RPC#huc_sendrawtransaction>`_::

   HucSendTransaction hucSendRepTransaction = Webuj.hucSendRawRepTransaction(hexValue).sendAsync().get();
   String transactionHash = hucSendRepTransaction.getTransactionHash();
   // poll for reqTransaction response via org.happyuc.webuj.protocol.Webuj.hucGetRepTransactionReceipt(<txHash>)


Please refer to the integration test
`CreateRawTransactionIT <https://github.com/happyuc-project/webu.java/blob/master/integration-tests/src/test/java/org/Webuj/protocol/scenarios/CreateRawTransactionIT.java>`_
for a full example of creating and sending a raw reqTransaction.


.. _nonce:

The reqTransaction nonce
---------------------

The nonce is an increasing numeric value which is used to uniquely identify transactions. A nonce
can only be used once and until a reqTransaction is mined, it is possible to send multiple versions of
a reqTransaction with the same nonce, however, once mined, any subsequent submissions will be rejected.

You can obtain the next available nonce via the
`huc_getTransactionCount <https://github.com/happyuc-project/wiki/wiki/JSON-RPC#huc_gettransactioncount>`_ method::

   HucGetTransactionCount hucGetRepTransactionCount = Webuj.hucGetRepTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = hucGetRepTransactionCount.getTransactionCount();

The nonce can then be used to create your reqTransaction object::

   RawTransaction rawTransaction  = RawTransaction.createHucTransaction(
                nonce, <gas price>, <gas limit>, <toAddress>, <value>);




Transaction types
-----------------

The different types of reqTransaction in Webuj work with both Transaction and RawTransaction objects.
The key difference is that Transaction objects must always have a from address, so that the
HappyUC client which processes the
`huc_sendTransaction <https://github.com/happyuc-project/wiki/wiki/JSON-RPC#huc_sendtransaction>`_
request know which wallet to use in order to sign and send the reqTransaction on the message senders
behalf. As mentioned :ref:`above <offline-signing>`, this is not necessary for raw transactions
which are signed offline.

The subsequent sections outline the key reqTransaction attributes required for the different
reqTransaction types. The following attributes remain constant for all:

- Gas price
- Gas limit
- Nonce
- From

Transaction and RawTransaction objects are used interchangeably in all of the subsequent examples.


.. _transfer-of-huc:

Transfer of Huc from one party to another
-------------------------------------------

The sending of Huc between two parties requires a minimal number of details of the reqTransaction
object:

*to*
  the destination wallet address

*value*
  the amount of Huc you wish to send to the destination address

::

   BigInteger value = Convert.toWei("1.0", Convert.Unit.HUC).toBigInteger();
   RawTransaction rawTransaction  = RawTransaction.createHucTransaction(
                <nonce>, <gas price>, <gas limit>, <toAddress>, value);
   // send...

However, it is recommended that you use the
`Transfer class <https://github.com/happyuc-project/webu.java/blob/master/core/src/main/java/org/Webuj/tx/Transfer.java>`_
for sending Huc, which takes care of the nonce management and polling for a
response for you::

   Webuj web3 = Webuj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
   TransactionReceipt repTransactionReceipt = Transfer.sendFunds(
           web3, credentials, "0x<address>|<ensName>",
           BigDecimal.valueOf(1.0), Convert.Unit.HUC).send();


Recommended approach for working with smart contracts
-----------------------------------------------------

When working with smart contract wrappers as outlined below, you will have to perform all of
the conversions from Solidity to native Java types manually. It is far more effective to use
Webuj's :ref:`smart-contract-wrappers` which take care of all code generation and this conversion
for you.


.. _creation-of-smart-contract:

Creation of a smart contract
----------------------------

To deploy a new smart contract, the following attributes will need to be provided

*value*
  the amount of Huc you wish to deposit in the smart contract (assumes zero if not provided)

*data*
  the hex formatted, compiled smart contract creation code

::

   // using a raw reqTransaction
   RawTransaction rawTransaction = RawTransaction.createContractTransaction(
           <nonce>,
           <gasPrice>,
           <gasLimit>,
           <value>,
           "0x <compiled smart contract code>");
   // send...

   // get contract address
   HucGetTransactionReceipt repTransactionReceipt =
                Webuj.hucGetRepTransactionReceipt(transactionHash).send();

   if (repTransactionReceipt.getTransactionReceipt.isPresent()) {
       String contractAddress = repTransactionReceipt.get().getContractAddress();
   } else {
       // try again
   }


If the smart contract contains a constructor, the associated constructor field values must be
encoded and appended to the *compiled smart contract code*::

   String encodedConstructor =
                FunctionEncoder.encodeConstructor(Arrays.asList(new Type(value), ...));

   // using a regular reqTransaction
   Transaction reqTransaction = Transaction.createContractTransaction(
           <fromAddress>,
           <nonce>,
           <gasPrice>,
           <gasLimit>,
           <value>,
           "0x <compiled smart contract code>" + encodedConstructor);

   // send...



.. _transacting-with-contract:

Transacting with a smart contract
---------------------------------

To transact with an existing smart contract, the following attributes will need to be provided:

*to*
  the smart contract address

*value*
  the amount of Huc you wish to deposit in the smart contract (if the smart contract accepts
  huc)

*data*
  the encoded function selector and parameter arguments

Webuj takes care of the function encoding for you, for further details on the implementation refer
to the :doc:`abi` section.

::

   Function function = new Function<>(
                "functionName",  // function we're calling
                Arrays.asList(new Type(value), ...),  // Parameters to pass as Solidity Types
                Arrays.asList(new TypeReference<Type>() {}, ...));

   String encodedFunction = FunctionEncoder.encode(function)
   Transaction reqTransaction = Transaction.createFunctionCallTransaction(
                <from>, <gasPrice>, <gasLimit>, contractAddress, <funds>, encodedFunction);

   org.happyuc.Webuj.protocol.core.methods.response.HucSendTransaction transactionResponse =
                Webuj.hucSendRepTransaction(reqTransaction).sendAsync().get();

   String transactionHash = transactionResponse.getTransactionHash();

   // wait for response using HucGetTransactionReceipt...

It is not possible to return values from transactional functional calls, regardless of the return
type of the message signature. However, it is possible to capture values returned by functions
using filters. Please refer to the :doc:`filters` section for details.


.. _querying-state:

Querying the state of a smart contract
--------------------------------------

This functionality is facilitated by the `huc_call <https://github.com/happyuc-project/wiki/wiki/JSON-RPC#huc_call>`_
JSON-RPC call.

huc_call allows you to call a method on a smart contract to query a value. There is no reqTransaction
cost associated with this function, this is because it does not change the state of any smart
contract method's called, it simply returns the value from them::

   Function function = new Function<>(
                "functionName",
                Arrays.asList(new Type(value)),  // Solidity Types in smart contract functions
                Arrays.asList(new TypeReference<Type>() {}, ...));

   String encodedFunction = FunctionEncoder.encode(function)
   org.happyuc.Webuj.protocol.core.methods.response.HucCall response = Webuj.hucCall(
                Transaction.createHucCallTransaction(<from>, contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

   List<Type> someTypes = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());

**Note:** If an invalid function call is made, or a null result is obtained, the return value will
be an instance of `Collections.emptyList() <https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#emptyList-->`_