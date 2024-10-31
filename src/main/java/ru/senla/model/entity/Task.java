package ru.senla.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.format.annotation.DateTimeFormat;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@NamedEntityGraph(
    name = "task_entity_graph",
    attributeNodes = {
      @NamedAttributeNode("project"),
      @NamedAttributeNode("assignedTo"),
      @NamedAttributeNode("createdBy")
    })
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private Project project;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assigned_to", referencedColumnName = "id")
  private UserProfile assignedTo;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", referencedColumnName = "id")
  private UserProfile createdBy;

  @Column(name = "due_date", nullable = false)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime dueDate;

  @Column(name = "status", nullable = false)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private StatusType status;

  @Column(name = "priority", nullable = false)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private PriorityType priority;

  public Task(Long task) {
    this.id = task;
  }
}
