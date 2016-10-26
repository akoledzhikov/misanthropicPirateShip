package arr.pirate.ship.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import arr.pirate.ship.service.StorageService;
import arr.pirate.ship.util.ApplicationContextUtil;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@Entity
@Table(name = "Content")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long challengeInstanceId;

	@Column(length = 128)
	private String originalName;

	@Column(length = 128)
	private String contentType;

	@Column(length = 64)
	private String docType;

	private Date createdOn;

	@Transient
	private byte[] content;

	@Transient
	private boolean setContentCalled = false;

	public Content() {
		super();
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String location) {
		this.originalName = location;
	}

	public long getId() {
		return id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	@Transient
	public byte[] getContent() throws Exception {
		if (content == null) {
			content = ApplicationContextUtil.getInstance().getBean(StorageService.class).getContentAsBytes(this);
		}

		return content;
	}

	@Transient
	public void setContent(byte[] content) {
		setContentCalled = true;
		this.content = content;
	}

	@PostPersist
	@PostUpdate
	private void storeContentOnFileSystem() throws Exception {
		if (setContentCalled) {
			ApplicationContextUtil.getInstance().getBean(StorageService.class).storeContent(this, content);
		}
	}

	public long getChallengeInstanceId() {
		return challengeInstanceId;
	}

	public void setChallengeInstanceId(long challengeInstanceId) {
		this.challengeInstanceId = challengeInstanceId;
	}

}
