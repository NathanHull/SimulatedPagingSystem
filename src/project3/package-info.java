/**
 * 
 */
/**
 * @author Nathan Hull
 * CIS452 Project 3
 * Simulated Paging System
 * 

 
CIS 452  Program 3
Simulated Paging System
Overview
This programming assignment is designed primarily to improve your understanding of memory allocation protocols, operating system kernel data structures, and memory management hardware.  It also incorporates an additional element of modern operating systems: user-interface design and graphical display.

The project may be written in your language of choice; it is primarily a design exercise.  You may use any graphical or display library you choose.  Suggested combinations are C/GTK+, C++/Qt, Perl/Tk, Java/Swing, Python/TkInter/PyQt, php/Javascript/html, C#/asp.NET, etc; the decision is yours.
 
Activities

    This is an individual programming assignment.
    It is not system code, but rather a typical application: design, code, test, repeat.  As usual, be prepared to demo your program.
    Submit:
        your well-documented source code
        sample output and/or screen shots
         a design document:
            describe your data structures thoroughly.  For example, explain how you manage data objects such as the page tables.
            describe your algorithms thoroughly.  For example explain how physical memory is allocated to a process?

 
Program Specification: Simulated Paging System

    You are to create a program that simulates the operation of an operating system, specifically the memory management subsystem and the process loader.  The computer system your program is to simulate has the following characteristics:
        4K bytes of physical memory
        page/frame size is 512 bytes
        process logical address space is partitioned similar to that described in Lab #9 for Linux.  However, this project only considers the text and data segments.
     
    The operating system loader is invoked when the exec() system call attempts to start a new process.  The loader first reads the header file of the executable (the a.out) to ascertain its memory requirements (recall that the readelf utility from Lab 9 performed the same function).  It then loads the executable from the disk into available free frames of memory (as discussed in class).  The appropriate fields of the process' page table are updated with the mapping info, and control is set to jump to the process' entry point (the beginning of main()).  The goal is to simulate the operation of the operating system loader.

    Think about the mechanics of implementing a paged memory management protocol:
        your program (simulating the loader) is presented with the size of the text segment and the size of the data segment of an executable (i.e. it "reads" the header of the arriving executable).
        knowing the page size (512 bytes), the operating system loader determines the number of pages required for the text and data segments.  Note: they are mapped separately.
        like an operating system, your simulation program implements a page table data structure for each process, and also keeps track of all free frames.  Knowing the number of pages in each segment of the arriving executable, and the location of all free frames, it maps the pages of the process into simulated physical memory.
        your simulation program should then display the process page table showing the mapping of pages to frames for that process
        your simulation program must also display the page frame table (showing the memory map of physical memory and its contents)

For example, suppose a process has a text segment of size 1044 bytes and a data segment of 940 bytes.  Since pages are 512 bytes in size, this process is initially composed of 3 pages of text and 2 pages of data.  The loader will then "map" these 5 pages somewhere into the 8 frames of physical memory, appropriately updating the page table for this process.  The page table and the contents of physical memory will then be displayed..

     Your memory management program should perform a simulation of "executing" processes (i.e. it should implement simulated multiprogramming).  The simulation works like this:

        your program reads a trace tape of recorded process executions.  The trace tape contains two types of events. Each event is on a separate line and has one of the following formats:
        1. Process arrival:  ProcID    TextSize    DataSize
        2. Process termination:  ProcID    Halt
        where sizes are given in bytes.
        whenever a process "arrives" (event type #1), it should be placed into memory (i.e. mapped) as described above.
        whenever a process terminates (event type #2), its memory space should be reclaimed.
        after each event, your program should display the page table(s) and the current state of physical memory.  The exact format of the display is up to you.

Sample output:

        here is a portion of the trace tape representing three events:

        0    1044    940
        1    536      256
        0    Halt

        After executing these three events, your simulator will have:
            allocated and mapped 3 text pages and 2 data pages for Process 0
            allocated and mapped 2 text pages and 1 data page for Process 1
            reclaimed the frames released when process 0 terminates.

        After "executing" these three events, the state of the simulated system as maintained and displayed by your program would look something like this:

        Page table(s)
        Process 1:
                   Page    Frame
        Text       0          5
                      1          6
        Data       0          7

        Physical Memory / Frame Table
        Frame #    ProcID    Segment    Page #
            0
            1
            2
            3
            4
            5               1            Text          0
            6               1            Text          1
            7               1            Data          0
    note that in the system described, each segment is mapped separately and begins numbering its pages with page 0 (in other words, page the segments)

Begin by running your program on the sample input file (the recorded "trace" tape posted on the course info page).  Your program should "execute" one line of the input file at a time (under user control).  For example, pause after each event until the user hits the space bar, then execute the next line of the trace tape.

Note:  additional "test" input files will be posted on Demo Day.

Observations:  this assignment is primarily a design problem.  Use your knowledge of a typical system, as presented in the textbook and discussed in class, to guide your design choices.  For example, you might:

    create a PCB-type data structure for each process.  The PCB contains information such as pointers to the process' page tables, the page table length, the lengths of individual segments, etc.
    develop a page table data structure as would be maintained by the kernel for each process
    implement a frame table (to simulate the contents of physical memory)
    maintain (via a data structure) the free frame list


User Interface:  to complete the assignment fully, you must also utilize a user-interface / graphical display.  Part of the graphical display of the state of the above system, following the three trace tape events, might look something like this (I used C++/Qt in my simulation):



This display shows the contents of the physical memory portion of the system after Process 0 has released its frames.  The "Next" button is used to step through the simulation, reading and processing one line at a time from the input file (the trace tape of events).  Again, the exact format/design of the display is entirely up to you.

Note that you must also include the process page table(s) somewhere  in your graphical display.


Simulated Paging System

Ultimately, the goal is to create a pleasing and informative visual simulation/experience that helps the user understand how the memory management subsystem works to implement paging.  Design your simulation with this goal in mind.  For example, display the current trace tape event so that the user can see what caused the observed changes.  Another example:  color-code the pages in memory to help the user quickly see where a process has been mapped.

Some enhancements may qualify for extra credit.  For example, a "Back" button would be a great idea; it allows a user to cycle back and forth ("Undo") to truly understand what just happened in the simulation.  Creating a customizable system is another great idea: allow the user to specify the amount of physical memory in the system, and to specify the size of a frame.  This would support experiments such as "What happens if the frame size is 1KB, instead of 512 bytes"?

You are expected to use professional design standards for your visualization.  Consider partitioning your code using the Model/View/Controller (MVC) or the Model/View/Presenter (MVP) paradigms.  The idea is to logically isolate your display code from the memory management code to the extent possible, in order to facilitate testing and modfication.  Apply the best practices design and coding principles you have learned in your introductory classes.
  
Grading Rubric:

    Functionality
        'C' level:  implement a correctly working, text-based simulation that meets all specifications
        'A' level:  incorporate a GUI front end (display and control) onto your correctly working simulation
    Software Engineering
        your code follows good software engineering practices
        your solution works correctly
        your solution meets specifications
    Documentation
        your code must be well-documented and properly formatted
        you must include a complete Design Document describing your solution. It may include a Guide so the user knows how to use your simulation.


Extra credit (examples):
As always, an extra credit option is in addition to, not in place of, the originally specified program.  Make sure your simulation correctly processes the provided trace tape, meeting all specifications.

    implement virtual memory
        allow total process address space to exceed physical memory space
        you will need to add a simulated "disk" that implements swap space
        swap/page processes in/out of physical memory as necessary to execute them

    extend your mapping to include dynamic data
        create stack and heap segments
        you will need to add new event types to the trace tape in order to represent run-time changes in:
            stack space size (i.e. function calls)
            heap space size (i.e. malloc/new/free/delete)

    implement various advanced paging concepts
        multi-level paging
        translation lookaside buffer / associative memory

    other enhancements...

 */
package project3;