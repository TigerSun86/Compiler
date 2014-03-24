        .section        ".data"
        .align          4

G_A:    .word           0
G_B:    .word           0
G_C:    .word           0
G_A_Y:  .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0

        .section        ".text"
        .align          4
        .global         start
start:
        print G_A_Y + 36    ! Print array
        set G_A_Y,%l0       ! Assign array
        set 36,%l1          ! Array offset
        set -257,%l2
        st %l2,[%l0 + %l1]
        print G_A_Y + 36    ! Print array
        print G_A_Y + 0    ! Print array
        set G_A_Y,%l0       ! Assign array
        set 0,%l1          ! Array offset
        set -1111111,%l2
        st %l2,[%l0 + %l1]
        print G_A_Y + 0    ! Print array
        print G_C           ! Print varible
        set G_C,%l0         ! Assign varible
        set 65537,%l2
        st %l2,[%l0]
        print G_C           ! Print varible
exit_program
