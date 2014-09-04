package lab.core.dao.impl.vo;

import org.hibernate.type.Type;

public class HqlParameter
{
  private String name;
  private Object value;
  private Type type;

  public HqlParameter()
  {
  }

  public HqlParameter(Object value)
  {
    this.value = value;
  }

  public HqlParameter(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public HqlParameter(String name, Object value, Type type) {
    this.name = name;
    this.value = value;
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}