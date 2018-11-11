package de.fungistudii.enjhin.box2d.contactInterfaces;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.utils.Multiplexer;

/** a {@link ContactListener} that sends {@link Contact Contacts} to an {@link Array} of ContactListeners
 *  @author samuel */
public class CLMultiplexer extends Multiplexer<ContactListener> implements ContactListener {

	public CLMultiplexer(ContactListener... receivers) {
		super(receivers);
	}

	public CLMultiplexer(Array<ContactListener> receivers) {
		super(receivers);
	}

        /**@see ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact) */
	@Override
	public void beginContact(Contact contact) {
		for(ContactListener listener : receivers)
			listener.beginContact(contact);
	}

        /**@see ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold) */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		for(ContactListener listener : receivers){
			listener.preSolve(contact, oldManifold);
                }
	}

        /**@see ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse) */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		for(ContactListener listener : receivers)
			listener.postSolve(contact, impulse);
	}

        /**@see ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact) */
	@Override
	public void endContact(Contact contact) {
		for(ContactListener listener : receivers)
			listener.endContact(contact);
	}
}