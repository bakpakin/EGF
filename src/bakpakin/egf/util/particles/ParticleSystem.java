package bakpakin.egf.util.particles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.util.geom.Transform;

public class ParticleSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6611187926976274398L;

	private static int nextId = 0;

	Map<ParticleDef, List<Particle>> particles;
	private Random random = new Random();

	private int id;
	private String tag;

	private int particleCount;

	public ParticleSystem(int priority) {
		super(priority);
		particles = new HashMap<ParticleDef, List<Particle>>();
	}

	public ParticleSystem() {
		this(0);
	}

	@Override
	protected Matcher initMatcher() {
		id = nextId++;
		tag = "_ParticleSystem_" + id;
		return new Matcher()
		.requireTag(tag)
		.requireOne(ParticleDestroyer.class)
		.requireOne(ParticleEmitter.class);
	}

	@Override
	public void update() {
		updateParticles();
		super.update();
	}

	private void updateParticles() {
		float dt = getWorld().getDeltaf();
		for (Map.Entry<ParticleDef, List<Particle>> entry : particles.entrySet()) {
			ParticleDef def = entry.getKey();
			List<Particle> list = entry.getValue();
			Iterator<Particle> iter = list.iterator();
			while(iter.hasNext()) {
				Particle p = iter.next();
				p.lifetime -= dt;
				if (p.lifetime < 0) {
					iter.remove();
					particleCount--;
					continue;
				}
				
				p.delta.a += def.delta2Alpha*dt;
				p.delta.r += def.delta2Red*dt;
				p.delta.g += def.delta2Green*dt;
				p.delta.b += def.delta2Blue*dt;
				p.deltaOrientation += def.delta2Orientation*dt;
				p.deltaDirection += def.delta2Direction*dt;
				p.deltaSize += def.delta2Size*dt;
				p.deltaSpeed += def.delta2Speed*dt;
				
				p.orientation += (p.deltaOrientation + rand(def.wiggleOrientation))*dt;
				p.direction += (p.deltaDirection + rand(def.wiggleDirection))*dt;
				p.size += (p.deltaSize + rand(def.wiggleSize))*dt;
				p.color.a += (p.delta.a + rand(def.wiggleAlpha))*dt;
				p.color.r += (p.delta.r + rand(def.wiggleRed))*dt;
				p.color.g += (p.delta.g + rand(def.wiggleGreen))*dt;
				p.color.b += (p.delta.b + rand(def.wiggleBlue))*dt;
				p.speed += (p.deltaSpeed + rand(def.wiggleSpeed))*dt;
				final double angle = (Math.PI / 180 * p.direction);
				final float cos = (float)Math.cos(angle);
				final float sin = (float)Math.sin(angle);
				p.x += p.speed*cos*dt;
				p.y += p.speed*sin*dt;
			}
		}
	}

	public void createParticle(float x, float y, ParticleDef def) {
		List<Particle> list = particles.get(def);
		if (list == null) {
			list = new LinkedList<Particle>();
			particles.put(def, list);
		}
		Particle p = new Particle();
		list.add(p);
		
		p.totalLifetime = rand(def.lifeMin, def.lifeMax);
		p.x = x;
		p.y = y;
		p.direction = rand(def.directionMin, def.directionMax);
		p.orientation = rand(def.orientationMin, def.orientationMax);
		p.lifetime = p.totalLifetime;
		p.size = rand(def.sizeMin, def.sizeMax);
		p.speed = rand(def.speedMin, def.speedMax);
		p.color = new Color(
				rand(def.redMin, def.redMax),
				rand(def.greenMin, def.greenMax),
				rand(def.blueMin, def.blueMax),
				rand(def.alphaMin, def.alphaMax)
				);
		
		p.deltaDirection = def.deltaDirection;
		p.deltaOrientation = def.delta2Orientation;
		p.deltaSize = def.deltaSize;
		p.deltaSpeed = def.deltaSpeed;
		p.delta = new Color(
				def.deltaRed,
				def.deltaGreen,
				def.deltaBlue,
				def.deltaAlpha
				);
		particleCount++;

	}

	private float rand(float magnitude) {
		return (2f*random.nextFloat()-1f)*magnitude;
	}

	private float rand(float min, float max) {
		return ((min + max)/2f) + rand((max - min)/2f);
	}

	@Override
	public void update(Entity e) {
		Transform t = e.get(Transform.class);
		if (t == null)
			t = new Transform();
		ParticleEmitter em = e.get(ParticleEmitter.class);
		if (em != null) {
			em.setActiveTransform(t);
			handleEmitter(em);
		}
		ParticleDestroyer pd = e.get(ParticleDestroyer.class);
		if (pd != null) {
			pd.setActiveTransform(t);
			handleDestroyer(pd);
		}
	}

	private void handleDestroyer(ParticleDestroyer pd) {
		for (List<Particle> list : particles.values()) {
			for (Particle p : list) {
				if (pd.containsParticle(p)) {
					p.lifetime = 0;
				}
			}
		}	
	}

	private void handleEmitter(ParticleEmitter em) {
		for (Map.Entry<ParticleDef, Float> entry : em.streams.entrySet()) {
			ParticleDef type = entry.getKey();
			float num = (float)Math.ceil(entry.getValue()*getWorld().getDelta());
			for (int i = 0; i < num; i++) {
				Vector2f randPoint = em.shape.randomPoint();
				createParticle(randPoint.x, randPoint.y, type);
			}
		}
		for (Map.Entry<ParticleDef, Float> entry : em.bursts.entrySet()) {
			ParticleDef type = entry.getKey();
			float num = entry.getValue();
			for (int i = 0; i < num; i++) {
				Vector2f randPoint = em.shape.randomPoint();
				createParticle(randPoint.x, randPoint.y, type);
			}
		}
		em.bursts.clear();
	}

	public String getTag() {
		return tag;
	}

	public int getParticleCount() {
		return particleCount;
	}

}
