package lab.core.dao.impl.util;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab.core.dao.impl.vo.HqlParameter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.type.Type;

public abstract class HqlUtils
{
  public static void setQueryParameters(Query query, List<HqlParameter> args)
  {
    if (args != null)
      for (int i = 0; i < args.size(); i++) {
        HqlParameter arg = (HqlParameter)args.get(i);
        String argName = arg.getName();
        Object argValue = arg.getValue();
        Type argType = arg.getType();
        if (argName == null)
        {
          if (argType == null) {
            query.setParameter(i, argValue);
          }
          else {
            query.setParameter(i, argValue, argType);
          }

        }
        else if (argType == null) {
          if (Collection.class.isInstance(argValue)) {
            query.setParameterList(argName, (Collection)argValue);
          }
          else {
            query.setParameter(argName, argValue);
          }

        }
        else if (Collection.class.isInstance(argValue)) {
          query.setParameterList(argName, (Collection)argValue, argType);
        }
        else
          query.setParameter(argName, argValue, argType);
      }
  }

  public static String filterRemovable(String hql)
  {
    String newHql = hql;
    if ((!newHql.contains(" removed")) && (!newHql.contains(".removed"))) {
      String trimHql = newHql.trim();
      if ((trimHql.endsWith("and")) || (trimHql.endsWith("where"))) {
        newHql = newHql.concat(" removed = 0");
      }
      else if (trimHql.contains("where")) {
        newHql = newHql.concat(" and removed = 0");
      }
      else {
        newHql = newHql.concat(" where removed = 0");
      }
    }
    return newHql;
  }

  public static String removeOrders(String queryHql)
  {
    if (StringUtils.isEmpty(queryHql)) {
      return queryHql;
    }
    Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
    Matcher m = p.matcher(queryHql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, "");
    }
    m.appendTail(sb);
    return sb.toString();
  }

  public static String removeSelect(String queryHql)
  {
    if (StringUtils.isNotEmpty(queryHql)) {
      int beginPos = queryHql.toLowerCase().indexOf("from");
      return beginPos != -1 ? queryHql.substring(beginPos) : queryHql;
    }

    return queryHql;
  }

  public static String getCountHql(String queryHql)
  {
    return "select count(*) " + removeSelect(removeOrders(queryHql));
  }
}