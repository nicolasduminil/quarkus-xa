package fr.simplex_software.quarkus_xa.money_xfr.xa_resource;

import javax.transaction.xa.*;

public class RestXAResource implements XAResource
{
  private final String resourceName;
  private final RestOperation operation;
  private Xid currentXid;

  public RestXAResource(String resourceName, RestOperation operation)
  {
    this.resourceName = resourceName;
    this.operation = operation;
  }

  @Override
  public void start(Xid xid, int flags)
  {
    this.currentXid = xid;
  }

  @Override
  public void end(Xid xid, int flags)
  {
  }

  @Override
  public void forget(Xid xid)
  {
  }

  @Override
  public int getTransactionTimeout()
  {
    return 0;
  }

  @Override
  public int prepare(Xid xid) throws XAException
  {
    try
    {
      operation.prepare();
      return XA_OK;
    }
    catch (Exception e)
    {
      throw new XAException(XAException.XAER_RMERR);
    }
  }

  @Override
  public void commit(Xid xid, boolean onePhase) throws XAException
  {
    try
    {
      operation.execute();
    }
    catch (Exception e)
    {
      throw new XAException(XAException.XAER_RMERR);
    }
  }

  @Override
  public void rollback(Xid xid)
  {
    operation.cleanup();
  }

  @Override
  public boolean setTransactionTimeout(int seconds)
  {
    return false;
  }

  @Override
  public boolean isSameRM(XAResource xaResource)
  {
    return xaResource instanceof RestXAResource &&
      ((RestXAResource) xaResource).resourceName.equals(this.resourceName);
  }

  @Override
  public Xid[] recover(int flag)
  {
    return new Xid[0];
  }
}
