package io.github.pr0lebenimholz.discordutils.util;

import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {

    private static HashMap<String, ArrayList<EventListener>> fmlEvents;
    private static HashMap<String, ArrayList<EventListener>> events;

    public static void init() {
        fmlEvents = new HashMap<>();
        events = new HashMap<>();
    }

    public static void registerFmlEvent(Class<? extends FMLEvent> event, EventListener<? extends FMLEvent> listener) {
        String type = event.getSimpleName();
        if (!fmlEvents.containsKey(type)) fmlEvents.put(type, new ArrayList<>());
        fmlEvents.get(type).add(listener);
    }

    public static void registerEvent(Class<? extends Event> event, EventListener<? extends Event> listener) {
        String type = event.getSimpleName();
        if (!events.containsKey(type)) events.put(type, new ArrayList<>());
        events.get(type).add(listener);
    }

    public static void execute(FMLEvent event) {
        // don't use FMLEvent::getEventType because i can't use it in FMLEventHandler::register
        String key = event.getClass().getSimpleName();
        if (fmlEvents.containsKey(key) && !fmlEvents.get(key).isEmpty())
            fmlEvents.get(event.getClass().getSimpleName()).forEach(listener -> listener.execute(event));
    }

    public static void execute(Event event) {
        String key = event.getClass().getSimpleName();
        if (events.containsKey(key) && !events.get(key).isEmpty())
            events.get(key).forEach(listener -> listener.execute(event));
    }

    public interface EventListener<E> {
        void execute(E event);
    }
}
