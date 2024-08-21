Project Overview:

I worked with my project teammate and developed Android car-pooling application that connects customers who travel towards same destination
points
Used google maps for live location tracking and firebase to store all the customer details.
Developed a feedback and preference system for drivers and matched the customers to riders who share similar interests.


import json
from rich.console import Console
from rich.table import Table

def compare_json(json1, json2):
    # Initialize the console
    console = Console()

    # Create a table for side-by-side comparison
    table = Table(title="JSON Comparison", show_header=True, header_style="bold magenta")
    table.add_column("Key", justify="left", style="cyan", no_wrap=True)
    table.add_column("JSON1", justify="left", style="green")
    table.add_column("JSON2", justify="left", style="red")

    # Collect keys from both JSONs
    all_keys = set(json1.keys()).union(set(json2.keys()))

    for key in all_keys:
        value1 = json1.get(key, "[Missing]")
        value2 = json2.get(key, "[Missing]")

        if isinstance(value1, list) and isinstance(value2, list):
            diff1 = set(value1) - set(value2)
            diff2 = set(value2) - set(value1)
            value1 = f"[{', '.join(diff1)}]" if diff1 else "[Same]"
            value2 = f"[{', '.join(diff2)}]" if diff2 else "[Same]"
        elif value1 != value2:
            if value1 == "[Missing]":
                value1 = "[Missing]"
            if value2 == "[Missing]":
                value2 = "[Missing]"
        else:
            value1 = value2 = "[Same]"

        table.add_row(key, str(value1), str(value2))

    # Display the table
    console.print(table)

# Load the JSON files
with open('json1.json', 'r') as file:
    json1 = json.load(file)

with open('json2.json', 'r') as file:
    json2 = json.load(file)

# Compare the JSON files and display the differences
compare_json(json1, json2)
