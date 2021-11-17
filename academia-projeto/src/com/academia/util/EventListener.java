package com.academia.util;

import java.util.ArrayList;
import java.util.List;

public interface EventListener {

	public List<Listener> listeners = new ArrayList<>();

	public void addListener(Listener obj);

	public void notifyListener();
}
