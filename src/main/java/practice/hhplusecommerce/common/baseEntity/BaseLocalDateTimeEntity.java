package practice.hhplusecommerce.common.baseEntity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseLocalDateTimeEntity {

  @NotNull
  @CreatedDate
  @Comment("생성일")
  private LocalDateTime createdAt;

  @NotNull
  @LastModifiedDate
  @Comment("수정일")
  private LocalDateTime updatedAt;

}
