package lab.core.dao.impl.vo;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;
 

public class Page implements Serializable {
	private Object filter;//传入参数：查询条件
	private List<Order> orders;//传入参数：排序参数
	
 
	private int start = -1,//衍生参数：起始记录行
	limit = -1,//传入参数：每页记录数
	page = -1;//传入参数：当前页序号
	
	private List data;//返回值：返回查询出的数据
	private Integer totalCount;//返回值：查询结果总行数
	private Integer totalPageCount;//返回值：查询结果总页数

	
	
	public int getStart() {
		if(start==-1){
			start = this.getLimit()*(this.getPage()-1);
		}
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		if(limit==-1){
			limit = 10;
		}
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		if(page==-1){
			page =1;
		}
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Object getFilter() {
		return filter;
	}
	public void setFilter(Object filter) {
		this.filter = filter;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public int getMaxResult(){
		return start+limit-1;
	}
	 
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public Integer getTotalPageCount() {
		if(totalPageCount==null) {
			totalPageCount =( totalCount.intValue()+ limit-1)/limit;
		}
		return totalPageCount;
	}
	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
}
