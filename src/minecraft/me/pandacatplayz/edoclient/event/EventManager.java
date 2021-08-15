package me.pandacatplayz.edoclient.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventManager {

	private static final Map<Class<? extends EventData>, ArrayList<EventData>> REGISTRY_MAP = new HashMap<Class<? extends EventData>, ArrayList<EventData>>();
	
	private static void sortListValue(final Class<? extends EventData> clazz) {
		
		final ArrayList<EventData> flexableArray = new ArrayList<EventData>();
		
		for(final byte b : EventPriority.VALUE_ARRAY) {
			for(EventData methodData : EventManager.REGISTRY_MAP.get(clazz)) {
				if(methodData.priority == b) {
					flexableArray.add(methodData);
				}
			}
		}
		
		EventManager.REGISTRY_MAP.put(clazz, flexableArray);
		
	}
	
	private static boolean isMethodBad(final Method method) {
		return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
	}
	
	private static boolean isMethodBad(final Method method, final Class<? extends EventData> clazz) {
		return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
	}
	
	public static ArrayList<EventData> get(final Class<? extends Event> class1){
		return REGISTRY_MAP.get(class1);
	}
	
	public static void cleanMap(final boolean removeOnlyEmptyValues) {
		
		final Iterator<Map.Entry<Class<? extends EventData>, ArrayList<EventData>>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();
		
		while(iterator.hasNext()) {
			if(!removeOnlyEmptyValues || iterator.next().getValue().isEmpty()) {
				iterator.remove();
			}
		}
	}
	
	public static void unregister(final Object o, final Class<? extends EventData> clazz) {
		
		if(REGISTRY_MAP.containsKey(clazz)) {
			for(final EventData methodData : REGISTRY_MAP.get(clazz)) {
				if(methodData.source.equals(o)) {
					REGISTRY_MAP.get(clazz).remove(methodData);
				}
			}
		}
		
		cleanMap(true);
		
	}
	
	public static void unregister(final Object o) {
		
		for(ArrayList<EventData> flexableArray : REGISTRY_MAP.values()) {
			
			for(int i = flexableArray.size() -1; i >= 0; i--) {
				
				if(flexableArray.get(i).source.equals(o)) {
					flexableArray.remove(i);
				}
				
			}
			
		}
		
		cleanMap(true);
		
	}
	
	public static void register(final Method method, final Object o) {
		
		final Class<?> clazz = method.getParameterTypes()[0];
		
		final EventData methodData = new EventData(o, method, method.getAnnotation(EventTarget.class).value());
		
		if(!methodData.target.isAccessible()) {
			methodData.target.setAccessible(true);
		}
		
		if(REGISTRY_MAP.containsKey(clazz)) {
			
			if(!REGISTRY_MAP.get(clazz).contains(methodData)) {
				REGISTRY_MAP.get(clazz).add(methodData);
				sortListValue((Class<? extends EventData>) clazz);
			}
			
		}
		else {
			REGISTRY_MAP.put((Class<? extends EventData>) clazz, new ArrayList<EventData>() {
				
				{
					this.add(methodData);
				}
				
			});
		}
		
	}
	
	public static void register(final Object o, final Class<? extends EventData> clazz) {
		
		for(final Method method : o.getClass().getMethods()) {
			if(!isMethodBad(method, clazz)) {
				register(method, o);
			}
		}
		
	}
	
	public static void register(Object o) {
		for(final Method method : o.getClass().getMethods()) {
			if(!isMethodBad(method)) {
				register(method, o);
			}
		}
	}
	
}