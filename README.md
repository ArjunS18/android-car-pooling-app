Project Overview:

I worked with my project teammate and developed Android car-pooling application that connects customers who travel towards same destination
points
Used google maps for live location tracking and firebase to store all the customer details.
Developed a feedback and preference system for drivers and matched the customers to riders who share similar interests.


import json
import os
from deepdiff import DeepDiff
from yattag import Doc, indent
from weasyprint import HTML

# Function to generate the HTML report for a pair of JSON files
def generate_html_report(file1_name, file2_name, diff):
    doc, tag, text = Doc().tagtext()
    
    with tag('table', klass='diff-table'):
        with tag('tr'):
            with tag('th'):
                text(file1_name)
            with tag('th'):
                text(file2_name)

        # Loop over the differences
        for diff_type, changes in diff.items():
            for change in changes:
                with tag('tr'):
                    if diff_type == 'dictionary_item_added':
                        with tag('td', klass='diff-addition'):
                            text(f"Added: {change.path()}")
                        with tag('td', klass='diff-addition'):
                            text(f"Value: {json.dumps(change.t2, indent=4)}")
                    elif diff_type == 'dictionary_item_removed':
                        with tag('td', klass='diff-deletion'):
                            text(f"Removed: {change.path()}")
                        with tag('td', klass='diff-deletion'):
                            text(f"Value: {json.dumps(change.t1, indent=4)}")
                    elif diff_type == 'values_changed':
                        with tag('td', klass='diff-deletion'):
                            text(f"Old: {json.dumps(change.t1, indent=4)}")
                        with tag('td', klass='diff-addition'):
                            text(f"New: {json.dumps(change.t2, indent=4)}")

    # Return the HTML content for this comparison
    return indent(doc.getvalue())

# Directory containing the JSON files
json_directory = 'path_to_your_json_files'

# List all JSON files in the directory
json_files = [f for f in os.listdir(json_directory) if f.endswith('.json')]

# Initialize a document to accumulate all HTML content
doc, tag, text = Doc().tagtext()

with tag('html'):
    with tag('head'):
        doc.stag('meta', charset="utf-8")
        with tag('style'):
            text('''
                .diff-table { width: 100%; border-collapse: collapse; }
                .diff-table td { padding: 8px; border: 1px solid #ccc; }
                .diff-addition { background-color: #eaffea; }
                .diff-deletion { background-color: #ffecec; }
            ''')
    with tag('body'):
        # Compare each pair of JSON files and accumulate the HTML content
        for i in range(len(json_files)):
            for j in range(i + 1, len(json_files)):
                file1_name = json_files[i]
                file2_name = json_files[j]
                
                # Load the JSON files
                with open(os.path.join(json_directory, file1_name)) as f1, open(os.path.join(json_directory, file2_name)) as f2:
                    json1 = json.load(f1)
                    json2 = json.load(f2)
                
                # Compare the JSON objects
                diff = DeepDiff(json1, json2, view='tree')
                
                # Generate the HTML content for this pair of files
                comparison_html = generate_html_report(file1_name, file2_name, diff)
                
                # Add a section header for this comparison
                with tag('h2'):
                    text(f'Comparison between {file1_name} and {file2_name}')
                
                # Add the comparison HTML to the document
                doc.asis(comparison_html)

# Convert the accumulated HTML content to a single PDF
html_content = indent(doc.getvalue())
HTML(string=html_content).write_pdf('all_comparisons.pdf')

print('All comparisons have been saved to all_comparisons.pdf')
