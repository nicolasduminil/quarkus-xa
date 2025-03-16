package fr.simplex_software.quarkus_xa.money_xfr.xa_resource;

public interface RestOperation
{
  void prepare() throws Exception;
  void execute() throws Exception;
  void cleanup();
}
