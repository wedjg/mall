

/**
 * 邮件实体类
 * @author Liao Jiajian
 * @since 2019-05-21
 */
 @Data
 public class MailDto extends MessageDto {
 
	/**
	 * 附件路径
	 */
	private String filePath;
	/**
	 * 静态资源路径
	 */
	private String rscPath;
	/**
	 * 静态资源ID
	 */
	private String rscId;
	
	public MailDto mailDto(String to, String subject, String content) {
		this.to = to;
		this.subject = subject;
		this.content = content;
	}
	
	public MailDto mailDto(String to, String subject, String content, String filePath) {
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.filePath = filePath;
	}
 }