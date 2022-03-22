package io.github.pr0lebenimholz.discordutils.util;

import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: 01.03.22 make this easier to use
/**
 * Redirects all events fired by forge to modules which are listening to those events.
 *
 * @author fivekWBassMachine
 */
public class EventHandler {

    private static HashMap<String, ArrayList<EventListener>> fmlEvents;
    private static HashMap<String, ArrayList<EventListener>> events;

    /**
     * Initiates the handler.
     */
    public static void init() {
        fmlEvents = new HashMap<>();
        events = new HashMap<>();
    }

    // TODO: 01.03.22 add Side parameter
    /**
     * Registers an event i.e. tells the handler to redirect an event to a module.
     *
     * @param event The event to listen to
     * @param listener The listener to call when the event is fired
     */
    public static void registerFmlEvent(Class<? extends FMLEvent> event, EventListener<? extends FMLEvent> listener) {
        String type = event.getSimpleName();
        if (!fmlEvents.containsKey(type)) fmlEvents.put(type, new ArrayList<>());
        fmlEvents.get(type).add(listener);
    }

    // TODO: 01.03.22 add Side parameter
    /**
     * Registers an event i.e. tells the handler to redirect an event to a module.
     *
     * @param event The event to listen to
     * @param listener The listener to call when the event is fired
     */
    public static void registerEvent(Class<? extends Event> event, EventListener<? extends Event> listener) {
        String type = event.getSimpleName();
        if (!events.containsKey(type)) events.put(type, new ArrayList<>());
        events.get(type).add(listener);
    }

    /**
     * The listener called by the main class.
     *
     * @param event The event which is fired by forge
     */
    public static void execute(FMLEvent event) {
        // don't use FMLEvent::getEventType because i can't use it in FMLEventHandler::register and Forge may change stuff...
        String key = event.getClass().getSimpleName();
        /* TODO: 11.03.22 (when creating and/ or (?) joining a sp world)
         *  NullPointerException in next line after 'preparing spawn area' and the 'bar steps'
         *  `if (fmlEvents.containsKey(key) && !fmlEvents.get(key).isEmpty())`
         *  Additionally, the client keeps stuck in 'Loading World\n\nBuilding Terrain'
         */
        if (fmlEvents.containsKey(key) && !fmlEvents.get(key).isEmpty())
            fmlEvents.get(event.getClass().getSimpleName())
                    .forEach(listener -> listener.execute(event));
    }

    /**
     * The listener called by the main class.
     *
     * @param event The event which is fired by forge
     */
    public static void execute(Event event) {
        String key = event.getClass().getSimpleName();
        if (events.containsKey(key) && !events.get(key).isEmpty())
            events.get(key).forEach(listener -> listener.execute(event));
    }

    /**
     * The listener interface for the modules.
     *
     * @param <E> The event; either {@link FMLEvent} or {@link Event}.
     */
    public interface EventListener<E> {
        void execute(E event);
    }
}
