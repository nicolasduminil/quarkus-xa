Yes, the transactional feature can work even without database operations, particularly in distributed transaction scenarios. In your code, the @Transactional annotation is being used to manage transaction boundaries for REST client calls, which is a valid use case for the following reasons:

Transaction coordination: The transaction manager (Narayana in this case) can coordinate the transaction across multiple services/resources, even if they're just REST calls. This ensures atomicity across distributed operations.

Transaction synchronization: Your code is using transaction synchronizations (through registerSynchronization) which allows you to execute logic before and after transaction completion, regardless of whether there are database operations.

However, there are important considerations:

The REST endpoints being called must be transaction-aware to participate in the distributed transaction. The remote services need to support transaction protocols (like XA or 2PC).

Without a resource manager (like a database), the transaction manager is primarily providing:

Transaction context propagation

Synchronization point management

Transaction lifecycle management (begin, commit, rollback)

Here's how you could enhance the current implementation to make better use of the transaction: [1]

This implementation provides transaction demarcation and synchronization points, which can be useful for:

Logging and monitoring

Coordinating multiple REST calls in a single transaction

Ensuring proper cleanup in case of failures

Managing transaction timeouts

Providing consistent error handling

Even without database operations, this transactional wrapper provides valuable transaction management capabilities for distributed operations.

You're absolutely right about this common misconception. Transactions are actually a broader concept that can be applied to any distributed operations that require atomicity and consistency, not just databases or JMS.

The transaction manager (like Narayana in your case) can coordinate different types of resources:

REST service calls

Database operations

Message queues

File system operations

Custom resources that implement proper transaction interfaces

What matters is the ability to coordinate multiple operations as a single atomic unit, ensuring that either all operations succeed or none of them do. This is particularly valuable in microservices architectures where you might need to coordinate changes across multiple services.

Your code is a good example of using transactions for REST calls, which can help maintain data consistency across distributed services. The transaction synchronization hooks you've implemented ( beforeCompletion and afterCompletion) provide valuable points for monitoring and managing the transaction lifecycle, regardless of the type of resource being managed.

This broader understanding of transactions is important for building reliable distributed systems, where consistency needs to be maintained across different types of operations and services. [1]

