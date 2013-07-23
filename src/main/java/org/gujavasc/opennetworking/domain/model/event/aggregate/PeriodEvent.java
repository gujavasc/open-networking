package org.gujavasc.opennetworking.domain.model.event.aggregate;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Embeddable
public class PeriodEvent {

	@Expose
	@Temporal(TemporalType.DATE)
	private Date startDate = new Date();

	@Expose
	@Temporal(TemporalType.DATE)
	private Date endDate = new Date();

	public PeriodEvent() {
	}

	public PeriodEvent(Date startDate, Date endDate) {
		if (startDate != null) {
			this.startDate = startDate;
		}

		if (endDate != null) {
			this.endDate = endDate;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}
