# Grimm — User Guide

Welcome to **Grimm**, a task-manager chatbot inspired by the dark and theatrical personality of *Hollow Knight’s* Grimm.  
This guide will help you get started, use its commands, and interact with it in the best way.

---

## Table of Contents
- [What is Grimm](#what-is-grimm)
- [Getting Started](#getting-started)
- [How to Use Grimm](#how-to-use-grimm)
- [Tips & Best Practices](#tips--best-practices)
---

## What is Grimm
Grimm is a text-based task manager with personality.  
It blends practical task-tracking with character dialogue inspired by the Nightmare King himself.  
If you like a little drama in your workflow, Grimm is for you.

**Features**
- Manage tasks: **todos**, **deadlines**, **events**
- Optional GUI support (future / experimental)
- Theatrical lore & flavour text

---

## Getting Started
1. **Clone or download** the repository.
2. **Compile or run** with Java (e.g. `javac` / `java` or from your IDE).
3. Grimm automatically **loads saved tasks** from `./data/grimm.txt` if present.
4. First run? You’ll start with an empty list until you add tasks.

---

## How to Use Grimm
Enter a command and press **Enter**. Grimm responds in character until you type the exit command.

| Command | Description | Example |
|--------|------------|---------|
| **todo** | Create a simple to-do task | `todo read book` |
| **deadline** | Create a task with a due date | `deadline submit report /by 2025-09-20` |
| **event** | Create an event with start and end times | `event project meeting /from 2025-09-21 14:00 /to 2025-09-21 15:00` |
| **list** | Display all current tasks | `list` |
| **mark** | Mark a task as done | `mark 3` |
| **unmark** | Mark a task as not done | `unmark 3` |
| **delete** | Remove a task | `delete 2` |
| **find** | Search for tasks by keyword | `find meeting` |
| **update** | Change an existing task’s description/type/date | `update 4 deadline report /by 2025-10-01` |
| **bye** | Exit and save tasks | `bye` |

---

## Tips & Best Practices

Use clear descriptions for easy searching.

Double-check task numbers with list before deleting.

Exit with bye to ensure tasks are saved.

 
