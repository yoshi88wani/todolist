package com.myapp.todolist.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * タスクの情報を表すモデルクラス
 */
public class Task {

	 /** タスクのユニークID */
    private int id;

    /** タスクのタイトル */
    private String title;

    /** タスクの詳細説明 */
    private String description;

    /** タスクの完了ステータス */
    private boolean completed;

    /** タスクの期限 */
    private LocalDate dueDate;

    /**
     * デフォルトのコンストラクタ。
     */
    public Task() {
    }

    /**
     * 各フィールドを初期化するコンストラクタ。
     * 
     * @param title タスクのタイトル
     * @param description タスクの詳細
     * @param dueDate タスクの期限
     */
    public Task(String title, String description,LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description セットする description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed セットする completed
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate セットする dueDate
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * @return "Task [id=" + id + ", title=" + title + ", description=" + description + ", completed=" + completed + ", dueDate=" + dueDate + "]" 
	 */
	@Override
	public String toString() {
	    return "Task [id=" + id + ", title=" + title + ", description=" + description + ", completed=" + completed + ", dueDate=" + dueDate + "]";
	}
	
	/**
	 * @param o オブジェクト
	 * @return this.id == t.id
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (!(o instanceof Task)) return false;
		Task t = (Task) o;
		return this.id == t.id;
	}
	
	/**
	 *@return Objects.hash(id) hash値
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
