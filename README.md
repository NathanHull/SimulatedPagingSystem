# Simulated UNIX Paging System
## Summary
This program is designed to simulate a UNIX paging system for memory management. As processes are run, they must be brought into memory. This program was written using the MVC design paradigm to ensure a high degree of decoupling for ease of testing, and freedom of design.

## Model
In this system, processes are described by a Process Control Block (PCB) with overall information such as the PID, page table, and total data length. The page table is made up of Frames, which contain their PID, type (data or text), page number, and size. Some of these parameters weren’t used in this implementation, but were included for extensibility as this project is interesting with room to grow.

## Algorithm
Frames in physical memory are identified by their index, as the physical memory is represented by an index of Frames objects. Thus, the Free Frame List is a queue (Linked List) of indices that are free, in the order they were freed. The first thing the program will do is check for empty frames in physical memory from 0 to the max, and add pages to those frames as it finds them. If there are still pages left over that need to be added, the program begins polling from the queue, removing indices from the head and replacing those frames with the new loading pages. 

When creating a new process, the Frame objects (pages) are created sequentially by decrementing the amount of bytes in the “frame size” variable from the amount of data or text bytes, creating a Frame of that size and that type, then adding it to the PCB’s page table. The last one will likely not be the full size a frame allows.

## Controller
The Controller (a Singleton object) contains information like (dynamic) stats such as physical memory, frame size, and number of pages. It also contains a queue to store the free frame list via index in the physical memory block, which is represented by an array. The queue allows frames to be popped off in order, and by implementing free frame recovery by allowing new references to take frames off the queue, this will ensure that only the frames that have not been used in the longest time will be removed to make room for others. Finally, the Controller holds a list of processes currently in memory via an array list of their PCBs.

## Directions
Upon startup, the user is given a safe screen as shown in Fig 0 – the Next button is not enabled until they’ve chosen an input file with data, and they can set different physical memory amounts or frame sizes using the textboxes in the top right (some issues with exact implementation, to be shown in demonstration). Once they’ve chosen a file, they may begin clicking the Next button to begin reading the file line by line. The process that was added or halted shows up with its page table in the left-hand table labeled Process Memory, and the changes to physical memory show up in the table to the right. When a process is halted, its page table is cleared and all the frames in physical memory are marked as free frames with an asterisk as shown in Fig 3. Finally, when another process comes in needing room for its pages, free frames in physical memory are replaced in the order they were freed. The end of the file is handled gracefully, stopping progress with a dialogue box.
