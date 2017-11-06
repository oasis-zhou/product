package rf.product.pub;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rf.product.conf.AppContext;

import java.beans.Introspector;
import java.util.List;
import java.util.Map;

public class TaskBundle<T> {

	private T t;
	private Map<String,Object> context = Maps.newHashMap();

	private List<Task<T>> workers = Lists.newArrayList();

	public TaskBundle(T t) {
		this.t = t;

	}

	public TaskBundle(T t, Map<String,Object> context){
		this.t = t;
		this.context = context;
	}

	public TaskBundle<T> register(Class<?> clazz) {
		return this.register(Introspector.decapitalize(clazz.getSimpleName()), Task.class);
	}

	@SuppressWarnings("unchecked")
	public TaskBundle<T> register(String beanName, Class<?> clazz) {
		Task<T> worker = AppContext.getBean(beanName, Task.class);
		this.workers.add(worker);
		return this;
	}

	public TaskBundle<T> register(Task<T> worker) {
		this.workers.add(worker);
		return this;
	}

	public void execute(){
		for(Task<T> workItem : this.workers) {
			workItem.execute(this.t, this.context);
		}
	}

	public Map<String, Object> getContext() {
		return context;
	}
}