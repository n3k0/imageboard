package org.belchan.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Data
@Entity
@Table(name="events")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	//TODO review this entity
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int at;

	private String name;
}