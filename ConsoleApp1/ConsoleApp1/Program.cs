using System;
using System.Collections.Generic;

public interface ICommand
{
    void Execute();
    void Undo();
}

public class Light
{
    public string Loc { get; }
    public Light(string loc) => Loc = loc;
    public void On() => Console.WriteLine($"   [Light] {Loc}: ON");
    public void Off() => Console.WriteLine($"   [Light] {Loc}: OFF");
}

public class Door
{
    public string Loc { get; }
    public Door(string loc) => Loc = loc;
    public void Open() => Console.WriteLine($"   [Door] {Loc}: OPEN");
    public void Close() => Console.WriteLine($"   [Door] {Loc}: CLOSE");
}

public class Thermostat
{
    private int temp = 20;
    public void Set(int degrees)
    {
        temp = degrees;
        Console.WriteLine($"   [Thermo] Temp set to: {temp}°C");
    }
}

public class Television
{
    public string Loc { get; }
    public Television(string loc) => Loc = loc;
    public void On() => Console.WriteLine($"   [TV] {Loc}: Power ON");
    public void Off() => Console.WriteLine($"   [TV] {Loc}: Power OFF");
}

public class LightOnCommand : ICommand
{
    private Light light;
    public LightOnCommand(Light l) => light = l;
    public void Execute() => light.On();
    public void Undo() => light.Off();
}

public class DoorOpenCommand : ICommand
{
    private Door door;
    public DoorOpenCommand(Door d) => door = d;
    public void Execute() => door.Open();
    public void Undo() => door.Close();
}

public class TempIncreaseCommand : ICommand
{
    private Thermostat thermo;
    private int delta = 2;
    public TempIncreaseCommand(Thermostat t) => thermo = t;
    public void Execute() => thermo.Set(22); 
    public void Undo() => thermo.Set(20);   
}

public class TVOnCommand : ICommand
{
    private Television tv;
    public TVOnCommand(Television t) => tv = t;
    public void Execute() => tv.On();
    public void Undo() => tv.Off();
}

public class RemoteControl
{
    private Stack<ICommand> history = new Stack<ICommand>();

    public void Press(ICommand cmd)
    {
        Console.WriteLine("\n EXECUTE");
        cmd.Execute();
        history.Push(cmd);
    }

    public void Undo()
    {
        if (history.Count > 0)
        {
            Console.WriteLine("\n<<< UNDO >>>");
            history.Pop().Undo();
        }
        else
        {
            Console.WriteLine("\n[ERROR] History is empty. Cannot undo a command that hasn't been executed.");
        }
    }
}

public class Program
{
    public static void Main(string[] args)
    {
        var light = new Light("Kitchen");
        var door = new Door("Garage");
        var thermo = new Thermostat();
        var tv = new Television("Living Room"); 

        var remote = new RemoteControl();

        Console.WriteLine("Testing Command Execution and History (3 types + 1 new) ");
        var cmdLight = new LightOnCommand(light);
        var cmdDoor = new DoorOpenCommand(door);
        var cmdThermo = new TempIncreaseCommand(thermo);
        var cmdTV = new TVOnCommand(tv);

        remote.Press(cmdLight);
        remote.Press(cmdDoor);
        remote.Press(cmdThermo);
        remote.Press(cmdTV);
        Console.WriteLine("\nTesting Undo Functionality");
        remote.Undo(); // Отмена TV ON -> TV OFF
        remote.Undo(); // Отмена Temp Increase -> Temp Decrease
        remote.Undo(); // Отмена Door Open -> Door Close
        remote.Undo(); // Отмена Light ON -> Light OFF

        remote.Undo(); 
    }
}